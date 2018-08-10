package com.dickow.chortlin.scenariotests;

import com.dickow.chortlin.core.Chortlin;
import com.dickow.chortlin.core.continuation.NoTransform;
import com.dickow.chortlin.core.message.Message;
import com.dickow.chortlin.testmodule.java.JavaSinkChannel;
import com.dickow.chortlin.testmodule.java.scenario3.Scenario3JavaInteractionEndpoint;
import com.dickow.chortlin.testmodule.java.scenario3.Scenario3JavaInteractionHandler;
import com.dickow.chortlin.testmodule.java.scenario3.Scenario3JavaTriggerEndpoint;
import com.dickow.chortlin.testmodule.java.scenario3.Scenario3JavaTriggerHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JavaScenarioTest3 {

    @Test
    void SetupTwoRegularJavaMethodsAndAnnotateOneOfThemWithEndpoint() {
        Chortlin.getNew().choreography()
                .onTrigger(
                        Scenario3JavaTriggerEndpoint.class,
                        "endpoint",
                        Scenario3JavaTriggerEndpoint::endpoint)
                .handleWith(new Scenario3JavaTriggerHandler())
                .addInteraction(
                        new NoTransform<>(),
                        Chortlin.get().interaction()
                                .onInteraction(
                                        Scenario3JavaInteractionEndpoint.class,
                                        "deleteUser",
                                        Scenario3JavaInteractionEndpoint::deleteUser)
                                .handleWith(new Scenario3JavaInteractionHandler())
                                .finish(new JavaSinkChannel<>()))
                .finish();
        Message<Integer> message = new Message<>();
        message.setPayload(100);
        Assertions.assertThrows(Exception.class, () -> new Scenario3JavaInteractionEndpoint().deleteUser(message));
    }
}
