package com.dickow.chortlin.scenariotests

import com.dickow.chortlin.core.Chortlin
import com.dickow.chortlin.core.configuration.trigger.Trigger
import com.dickow.chortlin.testmodule.kotlin.EndpointDefinitions
import com.dickow.chortlin.testmodule.kotlin.InteractionDefinitions
import com.dickow.chortlin.testmodule.kotlin.SinkChannel
import kotlin.test.Test
import kotlin.test.assertTrue

class ScenarioTest4 {

    @Test
    fun `create a sequential Interaction involving three methods`() {
        val sinkChannel = SinkChannel()

        val choreography = Chortlin.choreography()
                .onTrigger(EndpointDefinitions::class.java, "endpointWithStringInput", EndpointDefinitions::endpointWithStringInput)
                .mapInputTo { str -> str }
                .processWith { s -> s }
                .thenInteractWith(
                        Chortlin.interaction()
                                .onInteraction(
                                        InteractionDefinitions::class.java,
                                        "interaction",
                                        InteractionDefinitions::interaction)
                                .mapTo { s -> s }
                                .processWith { s -> s }
                                .thenInteractWith(
                                        Chortlin.interaction()
                                                .onInteraction(
                                                        InteractionDefinitions::class.java,
                                                        "interaction",
                                                        InteractionDefinitions::interaction)
                                                .mapTo { s -> s }
                                                .processWith { s -> s }
                                                .end())
                                .configureChannel(sinkChannel))
                .configureChannel(sinkChannel)
        assertTrue(choreography is Trigger)
    }
}