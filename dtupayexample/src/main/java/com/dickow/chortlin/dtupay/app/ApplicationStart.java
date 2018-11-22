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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.dickow.chortlin.core.choreography.participant.ParticipantFactory.external;
import static com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant;

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
        var merchant = participant(Merchant.class, "pay");
        var dtuPay = participant(DTUPayController.class, "pay");
        var dtuBank = participant(DTUBankIntegration.class, "transferMoney");
        var bank = participant(BankController.class, "transfer");

        var choreography = Choreography.Instance.builder()
                .interaction(client, merchant, "initiate payment")
                .interaction(merchant.getNonObservable(), dtuPay, "pay")
                .interaction(dtuPay.getNonObservable(), dtuBank, "integrate with bank")
                .interaction(dtuBank.getNonObservable(), bank, "perform transfer at bank")
                .returnFrom(bank, "return once transferred")
                .returnFrom(dtuBank, "return from the DTU bank integration")
                .returnFrom(dtuPay, "return from dtu pay")
                .returnFrom(merchant, "finished payment")
                .end()
                .runVisitor(new ASTInstrumentation(ByteBuddyInstrumentation.INSTANCE));

        InstrumentationStrategy.setStrategy(
                StrategyFactory.INSTANCE.createInMemoryChecker(List.of(choreography), true));
    }
}
