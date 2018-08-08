package com.dickow.chortlin.scenariotests

import com.dickow.chortlin.core.Chortlin
import com.dickow.chortlin.core.configuration.IChannel
import com.dickow.chortlin.core.message.IMessage
import com.dickow.chortlin.testmodule.kotlin.KotlinEndpointDefinitions
import com.dickow.chortlin.testmodule.kotlin.KotlinInteractionDefinitions
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
                .thenInteractWith(
                        Chortlin.interaction()
                                .onInteraction(
                                        KotlinInteractionDefinitions::class.java,
                                        "interactionInteger",
                                        KotlinInteractionDefinitions::interactionInteger)
                                .mapTo(this::map2)
                                .processWith(this::process2)
                                .end())
                .configureChannel(Channel())

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

    class Channel : IChannel<Int> {
        override fun send(message: IMessage<Int>) {
            KotlinInteractionDefinitions().interactionInteger(message.getPayload())
        }

    }
}