package com.dickow.chortlin.core.test.interaction

import com.dickow.chortlin.core.Chortlin
import com.dickow.chortlin.core.interaction.IChannel
import com.dickow.chortlin.core.message.IMessage

import kotlin.test.Test

class SequentialInteractionTest {

    @Test
    fun `setup simple sequential choreography`() {

        Chortlin.beginChoreography()
                .onTrigger(TestReceiver::receiveMsg)
                .mapTo { _, _, _ -> InputObject() }
                .processWith { _ -> TestMessage() }
                .thenInteractWith(TestReceiver2::receiveMsg)
                .via(TestChannel())
                .mapTo { _ -> InputObject2() }
                .processWithAndEnd { _ -> }
    }

    class TestMessage : IMessage

    class InputObject

    class InputObject2

    class TestReceiver {
        fun receiveMsg(p1: String, p2: Int, p3: Boolean) {

        }
    }

    class TestChannel : IChannel<TestMessage> {
        override fun send(message: TestMessage) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    class TestReceiver2 {
        fun receiveMsg(msg: IMessage): Int {
            return 4
        }
    }
}