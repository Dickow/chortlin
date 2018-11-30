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
import static com.dickow.chortlin.core.correlation.factory.CorrelationFactory.defineCorrelationSet;

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
        var merchant = participant(Merchant.class, "pay", Merchant::pay);
        var dtuPay = participant(DTUPayController.class, "pay", DTUPayController::pay);
        var dtuBank = participant(DTUBankIntegration.class, "transferMoney", DTUBankIntegration::transferMoney);
        var bank = participant(BankController.class, "transfer", BankController::transfer);

        var cset = defineCorrelationSet()
                .add(correlation(merchant, (String merchantId, PaymentDTO payment) -> merchantId)
                        .extendFromInput((String merchantId, PaymentDTO payment) -> merchantId)
                        .done())
                .add(correlation(dtuPay, (String merchantId, Integer amount, String token) -> merchantId)
                        .extendFromInput((String merchantId, Integer amount, String token) -> token)
                        .done())
                .add(correlation(dtuBank, (String merchantId, String customer, Integer amount) -> customer)
                        .noExtensions())
                .add(correlation(bank, TransactionDTO::getCustomer)
                        .noExtensions())
                .finish();

        var choreography = Choreography.Instance.builder()
                .interaction(client, merchant, "initiate payment")
                .interaction(merchant.nonObservable(), dtuPay, "pay")
                .interaction(dtuPay.nonObservable(), dtuBank, "integrate with bank")
                .interaction(dtuBank.nonObservable(), bank, "perform transfer at bank")
                .returnFrom(bank, "return once transferred")
                .returnFrom(dtuBank, "return from the DTU bank integration")
                .returnFrom(dtuPay, "return from dtu pay")
                .returnFrom(merchant, "finished payment")
                .end()
                .setCorrelationSet(cset)
                .runVisitor(new ASTInstrumentation(ByteBuddyInstrumentation.INSTANCE));

        InstrumentationStrategy.setStrategy(
                StrategyFactory.INSTANCE.createInMemoryChecker(List.of(choreography), true));
    }
}
