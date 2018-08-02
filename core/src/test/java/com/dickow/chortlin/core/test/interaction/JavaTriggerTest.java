package com.dickow.chortlin.core.test.interaction;

import com.dickow.chortlin.core.Chortlin;
import com.dickow.chortlin.core.configuration.trigger.Trigger;
import com.dickow.chortlin.core.handlers.IHandler2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JavaTriggerTest {

    private final String strInput = "Test input";
    private final Integer intInput = 201;

    @Test
    void InvokeConfiguredTriggerAndTestInputAndOutput() {
        Trigger trigger = Chortlin.INSTANCE.choreography()
                .onTrigger(JavaTriggerTest::endpoint)
                .handleWith(new Handler(strInput, intInput))
                .end();

        trigger.applyTo(new Object[]{strInput, intInput});
    }

    int endpoint(String input, int number) {
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
