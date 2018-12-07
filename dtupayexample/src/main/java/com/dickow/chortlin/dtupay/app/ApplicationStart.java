package com.dickow.chortlin.dtupay.app;

import com.dickow.chortlin.core.choreography.Choreography;
import com.dickow.chortlin.core.instrumentation.ASTInstrumentation;
import com.dickow.chortlin.core.instrumentation.ByteBuddyInstrumentation;
import com.dickow.chortlin.core.instrumentation.strategy.InstrumentationStrategy;
import com.dickow.chortlin.core.instrumentation.strategy.factory.StrategyFactory;
import com.dickow.chortlin.dtupay.bank.BankController;
import com.dickow.chortlin.dtupay.dtu.DTUBankIntegration;
import com.dickow.chortlin.dtupay.dtu.DTUPayController;
import com.dickow.chortlin.dtupay.merchant.Merchant;
import com.dickow.chortlin.dtupay.shared.dto.PaymentDTO;
import com.dickow.chortlin.dtupay.shared.dto.TransactionDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.dickow.chortlin.core.choreography.participant.ParticipantFactory.external;
import static com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant;
import static com.dickow.chortlin.core.correlation.factory.CorrelationFactory.correlation;
import static com.dickow.chortlin.core.correlation.factory.CorrelationFactory.defineCorrelation;

@EnableAutoConfiguration
@Configuration
@ComponentScan("com.dickow.chortlin.dtupay")
public class ApplicationStart {

    public static void main(String[] args) {
        configureChoreography();
        SpringApplication.run(ApplicationStart.class, args);
    }

    private static void configureChoreography() {
        var client = external("Client");
        var merchant = participant(Merchant.class);
        var dtuPay = participant(DTUPayController.class);
        var dtuBank = participant(DTUBankIntegration.class);
        var bank = participant(BankController.class);

        var cdef = defineCorrelation()
                .add(correlation(merchant.onMethod("pay", Merchant::pay),
                        "merchantId", (String merchantId, PaymentDTO payment) -> merchantId)
                        .extendFromInput("merchantId", (String merchantId, PaymentDTO payment) -> merchantId)
                        .done())
                .add(correlation(dtuPay.onMethod("pay", DTUPayController::pay),
                        "merchantId", (String merchantId, Integer amount, String token) -> merchantId)
                        .extendFromInput("userId", (String merchantId, Integer amount, String token) -> token)
                        .done())
                .add(correlation(dtuBank.onMethod("transferMoney", DTUBankIntegration::transferMoney),
                        "userId", (String merchantId, String customer, Integer amount) -> customer)
                        .noExtensions())
                .add(correlation(bank.onMethod("transfer", BankController::transfer),
                        "userId", TransactionDTO::getCustomer)
                        .noExtensions())
                .finish();

        var choreography = Choreography.Instance.builder()
                .interaction(client, merchant.onMethod("pay"), "initiate payment")
                .interaction(merchant, dtuPay.onMethod("pay"), "pay")
                .interaction(dtuPay, dtuBank.onMethod("transferMoney"), "integrate with bank")
                .interaction(dtuBank, bank.onMethod("transfer"), "perform transfer at bank")
                .returnFrom(bank.onMethod("transfer"), "return once transferred")
                .returnFrom(dtuBank.onMethod("transferMoney"), "return from the DTU bank integration")
                .returnFrom(dtuPay.onMethod("pay"), "return from dtu pay")
                .returnFrom(merchant.onMethod("pay"), "finished payment")
                .end()
                .setCorrelationSet(cdef)
                .runVisitor(new ASTInstrumentation(ByteBuddyInstrumentation.INSTANCE));

        InstrumentationStrategy.setStrategy(
                StrategyFactory.INSTANCE.createInMemoryChecker(List.of(choreography), true));
    }
}
