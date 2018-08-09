package com.dickow.chortlin.scenariotests;

import com.dickow.chortlin.core.Chortlin;
import com.dickow.chortlin.core.handlers.IHandler;
import com.dickow.chortlin.core.handlers.IHandler1;
import com.dickow.chortlin.testmodule.java.JavaEndpointDefinitions;
import com.dickow.chortlin.testmodule.java.JavaInteractionDefinitions;
import com.dickow.chortlin.testmodule.java.JavaSinkChannel;
import org.junit.jupiter.api.Test;

class JavaScenarioTest5 {

    @Test
    void CreateASequentialInteractionThatDoesNotInvolveNetworkTraffic() {
        Chortlin.INSTANCE.choreography()
                .onTrigger(
                        JavaEndpointDefinitions.class,
                        "endpoint1",
                        JavaEndpointDefinitions::endpoint1)
                .handleWith(new TestHandler())
                .addInteraction(
                        value -> value,
                        Chortlin.INSTANCE.interaction()
                                .onInteraction(
                                        JavaInteractionDefinitions.class,
                                        "interactionPoint1",
                                        JavaInteractionDefinitions::interactionPoint1)
                                .handleWith(new TestHandlerForString())
                                .finish(new JavaSinkChannel()))
                .finish();
        new JavaEndpointDefinitions().endpoint1();
    }

    class TestHandlerForString implements IHandler1<String, String, String> {

        @Override
        public String process(String input) {
            return null;
        }

        @Override
        public String mapInput(String arg) {
            return null;
        }
    }

    class TestHandler implements IHandler<String, String> {
        @Override
        public String mapInput() {
            return null;
        }

        @Override
        public String process(String input) {
            return null;
        }
    }
}
