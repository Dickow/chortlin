package com.dickow.chortlin.scenariotests;

import com.dickow.chortlin.core.Chortlin;
import com.dickow.chortlin.core.message.Channel;
import com.dickow.chortlin.core.message.IMessage;
import com.dickow.chortlin.testmodule.java.scenario2.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

class JavaScenarioTest2 {

    @Test
    void setupTwoRegularJavaMethodsAndAnnotateOneOfThemWithEndpoint() {
        Long id = 100L;
        String name = "Test man";

        Chortlin.INSTANCE.choreography()
                .onTrigger(
                        Scenario2JavaTriggerEndpoint.class,
                        "reduceRightsForUser",
                        Scenario2JavaTriggerEndpoint::reduceRightsForUser)
                .handleWith(new Scenario2JavaTriggerHandler())
                .addInteraction(
                        new Scenario2JavaInputTransformer(),
                        Chortlin.INSTANCE.interaction()
                                .onInteraction(
                                        Scenario2JavaInteractionEndpoint.class,
                                        "revokeRights",
                                        Scenario2JavaInteractionEndpoint::revokeRights)
                                .handleWith(new Scenario2JavaInteractionHandler(id))
                                .finish(new InMemoryChannel())
                ).finish();

        new Scenario2JavaTriggerEndpoint().reduceRightsForUser(id, name, false);

    }

    class InMemoryChannel implements Channel<Integer> {
        @Override
        public void send(@NotNull IMessage<Integer> message) {
            new Scenario2JavaInteractionEndpoint().revokeRights(message.getPayload());
        }
    }
}