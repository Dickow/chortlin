package com.dickow.chortlin.scenariotests

import com.dickow.chortlin.core.Chortlin
import com.dickow.chortlin.core.configuration.trigger.Trigger
import com.dickow.chortlin.scenariotests.shared.EndpointDefinitions
import com.dickow.chortlin.scenariotests.shared.InteractionDefinitions
import com.dickow.chortlin.scenariotests.shared.SinkChannel
import kotlin.test.Test
import kotlin.test.assertTrue

class ScenarioTest4 {

    @Test
    fun `Create a sequential Interaction involving three methods`() {
        val sinkChannel = SinkChannel()

        val choreography = Chortlin.choreography()
                .onTrigger(EndpointDefinitions::endpointWithStringInput)
                .mapInputTo { str -> str }
                .processWith { s -> s }
                .thenInteractWith(
                        Chortlin.interaction()
                                .onInteraction(InteractionDefinitions::interaction)
                                .mapTo { s -> s }
                                .processWith { s -> s }
                                .thenInteractWith(
                                        Chortlin.interaction()
                                                .onInteraction(InteractionDefinitions::interaction)
                                                .mapTo { s -> s }
                                                .processWith { s -> s }
                                                .end())
                                .configureChannel(sinkChannel))
                .configureChannel(sinkChannel)
        assertTrue(choreography is Trigger)
    }
}