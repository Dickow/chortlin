package com.dickow.chortlin.core.test.interaction

import com.dickow.chortlin.core.Chortlin
import com.dickow.chortlin.core.event.IEvent
import com.dickow.chortlin.core.interaction.IChannel
import com.dickow.chortlin.core.message.IMessage

import kotlin.test.Test

class SequentialInteractionTest {

    @Test
    fun `setup and call simple sequential choreography`() {
        Chortlin.onEvent<IEvent>()
                .receivedOn(TestReceiver::receiveMsg)
                .mapWith { _, _, _, _ -> TestEvent() }
                .processWith { _ -> TestMessage() as IMessage }
                .thenInteractWith(TestReceiver2::receiveMsg)
                .via(TestChannel())
    }

    class TestEvent : IEvent

    class TestMessage : IMessage

    class TestReceiver {
        fun receiveMsg(p1: String, p2: Int, p3: Boolean) {

        }
    }

    class TestChannel : IChannel<IMessage> {
        override fun send(message: IMessage) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    class TestReceiver2 {
        fun receiveMsg(msg: IMessage): Int {
            return 4
        }
    }
}