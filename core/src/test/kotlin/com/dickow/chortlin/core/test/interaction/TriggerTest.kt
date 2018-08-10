package com.dickow.chortlin.core.test.interaction

import com.dickow.chortlin.core.Chortlin
import com.dickow.chortlin.core.api.endpoint.Endpoint
import com.dickow.chortlin.core.continuation.Accumulator
import com.dickow.chortlin.core.handlers.IHandler2
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerTest {

    private val strInput = "Test input"
    private val intInput = 201

    @Test
    fun `invoke configured trigger and test input and output`() {
        val chortlin = Chortlin.getNew()
        val trigger = chortlin.choreography()
                .onTrigger(TriggerTest::class.java, "endpoint", TriggerTest::endpoint)
                .handleWith(Handler(strInput, intInput))
                .finish()

        val endpoint = Endpoint(TriggerTest::class.java, "endpoint")
        trigger.applyTo(arrayOf(strInput, intInput), Accumulator(endpoint))
    }

    fun endpoint(input: String, number: Int): Int {
        return number + input.toInt()
    }

    class Handler(val expectedStr: String, val expectedInt: Int) : IHandler2<String, Int, String, Int> {
        override fun process(input: String): Int {
            assertEquals(expectedInt.toString() + expectedStr, input)
            return 400
        }

        override fun mapInput(arg1: String, arg2: Int): String {
            assertEquals(expectedStr, arg1)
            assertEquals(expectedInt, arg2)
            return arg2.toString() + arg1
        }

    }

}