package com.dickow.chortlin.scenariotests

import com.dickow.chortlin.core.Chortlin
import com.dickow.chortlin.core.handlers.IHandler3
import com.dickow.chortlin.testmodule.kotlin.KotlinEndpointDefinitions
import kotlin.test.Test
import kotlin.test.assertEquals

class KotlinScenarioTest1 {

    @Test
    fun `setup a regular Java method and annotate it with the Endpoint annotation`() {
        val arg1 = 101
        val arg2 = "101"
        val arg3 = listOf("101", "102", "103")

        Chortlin.choreography()
                .onTrigger(
                        KotlinEndpointDefinitions::class.java,
                        "endpointWith3Inputs",
                        KotlinEndpointDefinitions::endpointWith3Inputs)
                .handleWith(Handler(arg1, arg2, arg3))
                .finish()

        KotlinEndpointDefinitions().endpointWith3Inputs(arg1, arg2, arg3)
    }

    class Handler constructor(
            private val intInput: Int,
            private val strInput: String,
            private val listInput: List<String>) : IHandler3<Int, String, List<String>, String, String> {
        override fun process(input: String): String {
            val expected = "${listInput.reduce { acc, s -> "$acc$s" }}$intInput$strInput"
            assertEquals(expected, input)
            return input
        }

        override fun mapInput(arg1: Int, arg2: String, arg3: List<String>): String {
            assertEquals(intInput, arg1)
            assertEquals(strInput, arg2)
            assertEquals(listInput, arg3)
            return "${arg3.reduce { acc, s -> "$acc$s" }}$arg1$arg2"
        }
    }
}