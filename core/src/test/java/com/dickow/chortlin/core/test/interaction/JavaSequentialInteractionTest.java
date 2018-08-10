package com.dickow.chortlin.core.test.interaction;

import com.dickow.chortlin.core.Chortlin;
import com.dickow.chortlin.core.message.Message;
import com.dickow.chortlin.core.test.interaction.shared.JavaSinkChannel;
import org.junit.jupiter.api.Test;


@SuppressWarnings("unused")
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
                                .processWith(o -> "")
                                .finish(new JavaSinkChannel<>())
                ).finish();
    }

    private class TestReceiver {
        Void receiveMsg(String p1, Integer p2, Boolean p3) {
            return null;
        }
    }

    private class InputObject {
    }

    private class InputObject2 {
    }

    private class Mapper {
        InputObject map1(String s, int i, boolean b) {
            return new InputObject();
        }

        InputObject2 map2(Message<String> msg) {
            return new InputObject2();
        }
    }

    private class Processor1 {
        Message<String> process(InputObject input) {
            return new Message<>();
        }
    }

    private class TestReceiver2 {
        int receiveMsg(Message<String> msg) {
            return 4;
        }
    }
}
