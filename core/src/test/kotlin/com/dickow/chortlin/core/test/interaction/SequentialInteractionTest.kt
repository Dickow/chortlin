package com.dickow.chortlin.core.test.interaction

import com.dickow.chortlin.core.Chortlin
import com.dickow.chortlin.core.continuation.Transform
import com.dickow.chortlin.core.message.IMessage
import com.dickow.chortlin.core.test.interaction.shared.KotlinSinkChannel
import kotlin.test.Test

@Suppress("UNUSED_PARAMETER")
class SequentialInteractionTest {

    @Test
    fun `setup simple sequential choreography`() {
        Chortlin.choreography()
                .onTrigger(TestReceiver::class.java, "receiveMsg", TestReceiver::receiveMsg)
                .mapInputTo { _, _, _ -> InputObject() }
                .processWith { _ -> TestMessage() }
                .addInteraction(
                        TestMessageTransformer(),
                        Chortlin.interaction()
                                .onInteraction(TestReceiver2::class.java, "receiveMsg", TestReceiver2::receiveMsg)
                                .mapTo { _ -> InputObject2() }
                                .processWith { _ -> TestMessage() }
                                .finish(KotlinSinkChannel())
                ).finish()
    }

    class TestMessage : IMessage<String> {
        override fun getPayload(): String {
            return "Hello world"
        }
    }

    class InputObject

    class InputObject2

    class TestMessageTransformer : Transform<TestMessage, String> {
        override fun transform(value: TestMessage): String {
            return value.getPayload()
        }
    }

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