package com.dickow.chortlin.core.test.interaction

import com.dickow.chortlin.core.Chortlin
import com.dickow.chortlin.core.api.endpoint.Endpoint
import com.dickow.chortlin.core.continuation.Accumulator
import com.dickow.chortlin.core.handlers.IHandler1
import com.dickow.chortlin.core.message.Message
import com.dickow.chortlin.core.test.interaction.shared.KotlinSinkChannel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@Suppress("UNUSED_PARAMETER")
class InteractionTest {

    private val inputMap = mapOf(
            Pair("Hello", "World"),
            Pair("Yo", "Test"),
            Pair("Kotlin", "Is Great")
    )

    @Test
    fun `verify interaction can correctly be applied to input`() {
        val chortlin = Chortlin.getNew()
        val interaction = chortlin.interaction()
                .onInteraction(InteractionTest::class.java, "endpoint", InteractionTest::endpoint)
                .handleWith(Handler(inputMap))
                .finish(KotlinSinkChannel())
        val endpoint = Endpoint(InteractionTest::class.java, "endpoint")
        val message = Message<Map<String, String>>()
        message.payload = inputMap
        interaction.applyTo(arrayOf(message), Accumulator(endpoint))
    }

    private fun endpoint(map: Message<Map<String, String>>): Int {
        return 400
    }

    private class Handler(private val expectedMap: Map<String, String>) : IHandler1<Message<Map<String, String>>, Collection<String>, Set<String>> {
        override fun mapInput(arg: Message<Map<String, String>>): Collection<String> {
            assertEquals(expectedMap, arg.payload)
            return arg.payload!!.keys
        }

        override fun process(input: Collection<String>): Set<String> {
            assertTrue { expectedMap.keys.all { input.contains(it) } && input.size == expectedMap.keys.size }
            return input.toSet()
        }

    }
}