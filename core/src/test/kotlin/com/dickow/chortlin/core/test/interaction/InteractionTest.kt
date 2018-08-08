package com.dickow.chortlin.core.test.interaction

import com.dickow.chortlin.core.Chortlin
import com.dickow.chortlin.core.handlers.IHandler1
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
        val interaction = Chortlin.interaction()
                .onInteraction(InteractionTest::class.java, "endpoint", InteractionTest::endpoint)
                .handleWith(Handler(inputMap))
                .end()

        interaction.applyTo(arrayOf(inputMap))
    }

    private fun endpoint(map: Map<String, String>): Int {
        return 400
    }

    private class Handler(private val expectedMap: Map<String, String>) : IHandler1<Map<String, String>, Collection<String>, Set<String>> {
        override fun mapInput(arg: Map<String, String>): Collection<String> {
            assertEquals(expectedMap, arg)
            return arg.keys
        }

        override fun process(input: Collection<String>): Set<String> {
            assertTrue { expectedMap.keys.all { input.contains(it) } && input.size == expectedMap.keys.size }
            return input.toSet()
        }

    }
}