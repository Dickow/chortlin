package com.dickow.chortlin.scenariotests;

import com.dickow.chortlin.core.Chortlin;
import com.dickow.chortlin.core.configuration.ChortlinConfiguration;
import com.dickow.chortlin.testmodule.java.JavaEndpointDefinitions;
import com.dickow.chortlin.testmodule.java.JavaInteractionDefinitions;
import com.dickow.chortlin.testmodule.java.JavaSinkChannel;
import org.junit.jupiter.api.Test;

class JavaScenarioTest4 {

    @Test
    void createASequentialInteractionInvolvingThreeMethods() {
        JavaSinkChannel channel = new JavaSinkChannel();
        ChortlinConfiguration config = Chortlin.INSTANCE.choreography()
                .onTrigger(
                        JavaEndpointDefinitions.class,
                        "endpointWithStringInput",
                        JavaEndpointDefinitions::endpointWithStringInput)
                .mapInputTo(s -> s)
                .processWith(s -> s)
                .addInteraction(
                        value -> value,
                        Chortlin.INSTANCE.interaction()
                                .onInteraction(
                                        JavaInteractionDefinitions.class,
                                        "interactionPoint1",
                                        JavaInteractionDefinitions::interactionPoint1)
                                .mapTo(s -> s)
                                .processWith(s -> s)
                                .addInteraction(
                                        value -> value,
                                        Chortlin.INSTANCE.interaction()
                                                .onInteraction(
                                                        JavaInteractionDefinitions.class,
                                                        "interactionPoint1",
                                                        JavaInteractionDefinitions::interactionPoint1)
                                                .mapTo(s -> s)
                                                .processWith(s -> s)
                                                .finish(channel)
                                ).finish(channel)
                ).finish();
    }
}
