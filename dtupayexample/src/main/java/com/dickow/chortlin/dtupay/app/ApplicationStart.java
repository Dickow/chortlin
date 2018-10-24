package com.dickow.chortlin.dtupay.app;

import com.dickow.chortlin.core.choreography.Choreography;
import com.dickow.chortlin.core.instrumentation.ASTInstrumentation;
import com.dickow.chortlin.core.instrumentation.ByteBuddyInstrumentation;
import com.dickow.chortlin.core.instrumentation.strategy.InstrumentationStrategy;
import com.dickow.chortlin.core.instrumentation.strategy.StorageStrategy;
import com.dickow.chortlin.core.trace.TraceElement;
import com.dickow.chortlin.dtupay.bank.BankController;
import com.dickow.chortlin.dtupay.dtu.DTUBankIntegration;
import com.dickow.chortlin.dtupay.dtu.DTUPayController;
import com.dickow.chortlin.dtupay.merchant.Merchant;
import com.dickow.chortlin.dtupay.shared.Console;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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
        var checker = Choreography.Instance.builder()
                .foundMessage(participant(Merchant.class, "pay"), "iniate payment")
                .foundMessage(participant(DTUPayController.class, "pay"), "pay")
                .interaction(participant(DTUBankIntegration.class, "transferMoney"),
                        participant(BankController.class, "transfer"), "bank transfer")
                .end()
                .build()
                .applyInstrumentation(new ASTInstrumentation(ByteBuddyInstrumentation.INSTANCE))
                .createChecker();
        InstrumentationStrategy.setStrategy(new StorageStrategy() {
            @Override
            public <C> void store(@NotNull TraceElement<C> trace) {
                Console.println("Storage of trace %s", trace.toString());
            }
        });
    }
}
