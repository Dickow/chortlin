package com.dickow.chortlin.scenariotests;

import com.dickow.chortlin.core.Chortlin;
import com.dickow.chortlin.core.handlers.IHandler3;
import com.dickow.chortlin.testmodule.java.JavaEndpointDefinitions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class JavaScenarioTest1 {

    @Test
    void setupARegularJavaMethodAndAnnotateItWithTheEndpointAnnotation() {
        Integer arg1 = 101;
        String arg2 = "101";
        List<String> arg3 = Arrays.asList("101", "102", "103");

        Chortlin.getNew().choreography()
                .onTrigger(
                        JavaEndpointDefinitions.class,
                        "endpointWith3Inputs",
                        JavaEndpointDefinitions::endpointWith3Inputs)
                .handleWith(new Handler(arg1, arg2, arg3))
                .finish();

        new JavaEndpointDefinitions().endpointWith3Inputs(arg1, arg2, arg3);
    }

    class Handler implements IHandler3<Integer, String, List<String>, String, String> {
        private final Integer intInput;
        private final String strInput;
        private final List<String> listInput;

        Handler(Integer intInput, String strInput, List<String> listInput) {
            this.intInput = intInput;
            this.strInput = strInput;
            this.listInput = listInput;
        }

        @Override
        public String mapInput(Integer arg1, String arg2, List<String> arg3) {
            Assertions.assertEquals(intInput, arg1);
            Assertions.assertEquals(strInput, arg2);
            Assertions.assertEquals(listInput, arg3);
            return String.join("", arg3).concat(String.valueOf(arg1)).concat(arg2);
        }

        @Override
        public String process(String input) {
            String expected = String.join("", listInput).concat(String.valueOf(intInput)).concat(strInput);
            Assertions.assertEquals(expected, input);
            return input;
        }
    }
}
