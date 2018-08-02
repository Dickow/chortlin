package com.dickow.chortlin.core.test.interaction

import com.dickow.chortlin.core.Chortlin
import com.dickow.chortlin.core.message.IMessage
import kotlin.test.Test

class SequentialInteractionTest {

    @Test
    fun `setup simple sequential choreography`() {
        Chortlin.choreography()
                .onTrigger(TestReceiver::receiveMsg)
                .mapInputTo { _, _, _ -> InputObject() }
                .processWith { _ -> TestMessage() }
                .thenInteractWith(
                        Chortlin.interaction()
                                .onInteraction(TestReceiver2::receiveMsg)
                                .mapTo { _ -> InputObject2() }
                                .processWith { _ -> TestMessage() }
                                .end()
                ).noChannel()
    }

    class TestMessage : IMessage<String> {
        override fun getPayload(): String {
            return "Hello world"
        }
    }

    class InputObject

    class InputObject2

    class TestReceiver {
        fun receiveMsg(p1: String, p2: Int, p3: Boolean) {

        }
    }

    class TestReceiver2 {
        fun receiveMsg(msg: String): Int {
            return 4
        }
    }
}