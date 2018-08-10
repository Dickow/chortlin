package com.dickow.chortlin.scenariotests

import com.dickow.chortlin.core.Chortlin
import com.dickow.chortlin.testmodule.kotlin.KotlinEndpointDefinitions
import com.dickow.chortlin.testmodule.kotlin.KotlinInteractionDefinitions
import com.dickow.chortlin.testmodule.kotlin.KotlinNoTransform
import com.dickow.chortlin.testmodule.kotlin.KotlinSinkChannel
import kotlin.test.Test
import kotlin.test.assertEquals

class KotlinScenarioTest5 {
    private val input = "Hello world this is a test message"
    private val mappedInput = "Mapped it, this message is so much better"
    private val output = 123456

    @Test
    fun `create a sequential Interaction that does not involve network traffic`() {
        Chortlin.choreography()
                .onTrigger(
                        KotlinEndpointDefinitions::class.java,
                        "endpointWithStringInput",
                        KotlinEndpointDefinitions::endpointWithStringInput)
                .mapInputTo(this::map)
                .processWith(this::process)
                .addInteraction(
                        KotlinNoTransform(),
                        Chortlin.interaction()
                                .onInteraction(
                                        KotlinInteractionDefinitions::class.java,
                                        "interactionInteger",
                                        KotlinInteractionDefinitions::interactionInteger)
                                .mapTo(this::map2)
                                .processWith(this::process2)
                                .finish(KotlinSinkChannel()))
                .finish()

        KotlinEndpointDefinitions().endpointWithStringInput(input)
    }

    fun map(str: String): String {
        assertEquals(input, str)
        return mappedInput
    }

    fun process(str: String): Int {
        assertEquals(mappedInput, str)
        return output
    }

    fun map2(int: Int): String {
        assertEquals(output, int)
        return int.toString()
    }

    fun process2(str: String): Int {
        assertEquals(output.toString(), str)
        return str.toInt()
    }
}