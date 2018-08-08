package com.dickow.chortlin.core.test.interaction;

import com.dickow.chortlin.core.Chortlin;
import com.dickow.chortlin.core.configuration.interaction.Interaction;
import com.dickow.chortlin.core.handlers.IHandler1;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

class JavaInteractionTest {

    private final Map<String, String> inputMap = new Hashtable<>();

    @Test
    void verifyInteractionCanCorrectlyBeAppliedToInput() {
        inputMap.put("Hello", "World");
        inputMap.put("Yo", "Test");
        inputMap.put("Kotlin", "Is Great");

        Interaction interaction = Chortlin.INSTANCE.interaction()
                .onInteraction(JavaInteractionTest.class, "endpoint", JavaInteractionTest::endpoint)
                .handleWith(new Handler(inputMap))
                .end();

        interaction.applyTo(new Object[]{inputMap});
    }

    private Integer endpoint(Map<String, String> input) {
        return 400;
    }

    class Handler implements IHandler1<Map<String, String>, Collection<String>, Set<String>> {
        private final Map<String, String> expectedMap;

        Handler(Map<String, String> expectedMap) {
            this.expectedMap = expectedMap;
        }


        @Override
        public Collection<String> mapInput(Map<String, String> arg) {
            Assertions.assertEquals(expectedMap, arg);
            return arg.keySet();
        }

        @Override
        public Set<String> process(Collection<String> input) {
            Assertions.assertTrue(input.containsAll(expectedMap.keySet()) && input.size() == expectedMap.keySet().size());
            return new HashSet<>(input);
        }
    }
}
