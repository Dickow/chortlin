package com.dickow.chortlin.core.test.interaction;

import com.dickow.chortlin.core.Chortlin;
import com.dickow.chortlin.core.api.endpoint.Endpoint;
import com.dickow.chortlin.core.configuration.trigger.Trigger;
import com.dickow.chortlin.core.continuation.Accumulator;
import com.dickow.chortlin.core.handlers.IHandler2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("FieldCanBeLocal")
class JavaTriggerTest {

    private final String strInput = "Test input";
    private final Integer intInput = 201;

    @Test
    void InvokeConfiguredTriggerAndTestInputAndOutput() {
        Chortlin chortlin = Chortlin.getNew();
        Trigger trigger = chortlin.choreography()
                .onTrigger(JavaTriggerTest.class, "endpoint", JavaTriggerTest::endpoint)
                .handleWith(new Handler(strInput, intInput))
                .finish();
        Endpoint endpoint = new Endpoint(JavaTriggerTest.class, "endpoint");
        trigger.applyTo(new Object[]{strInput, intInput}, new Accumulator(endpoint));
    }

    private int endpoint(String input, int number) {
        return number + Integer.parseInt(input);
    }

    class Handler implements IHandler2<String, Integer, String, Integer> {
        private final String expectedStr;
        private final Integer expectedInt;

        Handler(String expectedStr, Integer expectedInt) {
            this.expectedStr = expectedStr;
            this.expectedInt = expectedInt;
        }

        @Override
        public String mapInput(String arg1, Integer arg2) {
            Assertions.assertEquals(expectedStr, arg1);
            Assertions.assertEquals(expectedInt, arg2);
            return String.valueOf(arg2) + arg1;
        }

        @Override
        public Integer process(String input) {
            Assertions.assertEquals(String.valueOf(expectedInt) + expectedStr, input);
            return 400;
        }
    }
}
