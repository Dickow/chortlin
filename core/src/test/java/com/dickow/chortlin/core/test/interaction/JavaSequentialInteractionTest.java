package com.dickow.chortlin.core.test.interaction;

import com.dickow.chortlin.core.Chortlin;
import com.dickow.chortlin.core.message.Channel;
import com.dickow.chortlin.core.message.IMessage;
import com.dickow.chortlin.core.test.interaction.shared.JavaSinkChannel;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

class JavaSequentialInteractionTest {

    @Test
    void SetupSimpleSequentialChoreography() {
        Mapper mapper = new Mapper();
        Processor1 processor1 = new Processor1();
        Chortlin.INSTANCE.choreography()
                .onTrigger(TestReceiver.class, "receiveMsg", TestReceiver::receiveMsg)
                .mapInputTo(mapper::map1)
                .processWith(processor1::process)
                .addInteraction(
                        value -> value,
                        Chortlin.INSTANCE.interaction()
                                .onInteraction(TestReceiver2.class, "receiveMsg", TestReceiver2::receiveMsg)
                                .mapTo(mapper::map2)
                                .processWith(o -> new TestMessage())
                                .finish(new JavaSinkChannel<>())
                ).finish();
    }

    private class TestReceiver {
        Void receiveMsg(String p1, Integer p2, Boolean p3) {
            return null;
        }
    }

    private class TestMessage implements IMessage<String> {
        @Override
        public String getPayload() {
            return "Hello world";
        }
    }

    private class InputObject {
    }

    private class InputObject2 {
    }

    private class TestChannel implements Channel<TestMessage> {

        @Override
        public void send(@NotNull IMessage<TestMessage> message) {

        }
    }

    private class Mapper {
        InputObject map1(String s, int i, boolean b) {
            return new InputObject();
        }

        InputObject2 map2(TestMessage msg) {
            return new InputObject2();
        }
    }

    private class Processor1 {
        TestMessage process(InputObject input) {
            return new TestMessage();
        }
    }

    private class TestReceiver2 {
        int receiveMsg(TestMessage msg) {
            return 4;
        }
    }
}
