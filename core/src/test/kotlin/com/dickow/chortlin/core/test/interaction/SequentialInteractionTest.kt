package com.dickow.chortlin.core.test.interaction

import com.dickow.chortlin.core.Chortlin
import com.dickow.chortlin.core.continuation.Transform
import com.dickow.chortlin.core.message.Message
import com.dickow.chortlin.core.test.interaction.shared.KotlinSinkChannel
import kotlin.test.Test

@Suppress("UNUSED_PARAMETER")
class SequentialInteractionTest {

    @Test
    fun `setup simple sequential choreography`() {
        val chortlin = Chortlin.getNew()
        chortlin.choreography()
                .onTrigger(TestReceiver::class.java, "receiveMsg", TestReceiver::receiveMsg)
                .mapInputTo { _, _, _ -> InputObject() }
                .processWith { _ -> "" }
                .addInteraction(
                        TestMessageTransformer(),
                        chortlin.interaction()
                                .onInteraction(TestReceiver2::class.java, "receiveMsg", TestReceiver2::receiveMsg)
                                .mapTo { _ -> InputObject2() }
                                .processWith { _ -> "" }
                                .finish(KotlinSinkChannel())
                ).finish()
    }

    class InputObject

    class InputObject2

    class TestMessageTransformer : Transform<String, String> {
        override fun transform(value: String): String {
            return value
        }
    }

    class TestReceiver {
        fun receiveMsg(p1: String, p2: Int, p3: Boolean) {
        }
    }

    class TestReceiver2 {
        fun receiveMsg(msg: Message<String>): Int {
            return 4
        }
    }
}