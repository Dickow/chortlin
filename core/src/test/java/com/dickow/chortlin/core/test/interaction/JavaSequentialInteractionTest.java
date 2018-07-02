package com.dickow.chortlin.core.test.interaction;

import com.dickow.chortlin.core.Chortlin;
import org.junit.jupiter.api.Test;

public class JavaSequentialInteractionTest {

    @Test
    public void SetupAndCallSimpleSequentialChoreography() {
        Chortlin.INSTANCE.onEvent().receivedOn(TestReceiver::receiveMsg);

    }

    private class TestReceiver {
        public Void receiveMsg(String p1, Integer p2, Boolean p3) {
            return null;
        }
    }
}
