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
        var choreography = Choreography.Instance.builder()
                .foundMessage(participant(Merchant.class, "pay"), "initiate payment")
                .foundMessage(participant(DTUPayController.class, "pay"), "pay")
                .interaction(participant(DTUBankIntegration.class, "transferMoney"),
                        participant(BankController.class, "transfer"), "bank transfer")
                .returnFrom(participant(BankController.class, "transfer"), "return from bank transfer")
                .returnFrom(participant(DTUBankIntegration.class, "transferMoney"), "return from integration")
                .returnFrom(participant(DTUPayController.class, "pay"), "return from DTU")
                .returnFrom(participant(Merchant.class, "pay"), "return from Merchant solution")
                .end()
                .runVisitor(new ASTInstrumentation(ByteBuddyInstrumentation.INSTANCE));

        InstrumentationStrategy.setStrategy(
                StrategyFactory.INSTANCE.createInMemoryChecker(List.of(choreography), true));
    }
}
