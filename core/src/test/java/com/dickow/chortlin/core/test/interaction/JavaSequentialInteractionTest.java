package com.dickow.chortlin.core.test.interaction;

import com.dickow.chortlin.core.Chortlin;
import com.dickow.chortlin.core.interaction.IChannel;
import com.dickow.chortlin.core.message.IMessage;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

public class JavaSequentialInteractionTest {

    @Test
    public void SetupSimpleSequentialChoreography() {
        var mapper = new Mapper();
        var processor1 = new Processor1();
        Chortlin.INSTANCE.beginChoreography()
                .onTrigger(TestReceiver::receiveMsg)
                .mapTo(mapper::map1)
                .processWith(processor1::process)
                .thenInteractWith(TestReceiver2::receiveMsg)
                .via(new TestChannel())
                .mapTo(mapper::map2)
                .processWithAndEnd(o -> null);
    }

    private class TestReceiver {
        public Void receiveMsg(String p1, Integer p2, Boolean p3) {
            return null;
        }
    }

    private class TestMessage implements IMessage {
    }

    private class InputObject {
    }

    private class InputObject2 {
    }

    private class TestChannel implements IChannel<TestMessage> {

        @Override
        public void send(@NotNull TestMessage message) {

        }
    }

    private class Mapper {
        public InputObject map1(String s, int i, boolean b) {
            return new InputObject();
        }

        public InputObject2 map2(TestMessage msg) {
            return new InputObject2();
        }
    }

    private class Processor1 {
        public TestMessage process(InputObject input) {
            return new TestMessage();
        }
    }

    private class TestReceiver2 {
        public int receiveMsg(IMessage msg) {
            return 4;
        }
    }
}
