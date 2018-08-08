package com.dickow.chortlin.scenariotests

import com.dickow.chortlin.core.Chortlin
import com.dickow.chortlin.core.configuration.trigger.Trigger
import com.dickow.chortlin.testmodule.kotlin.KotlinEndpointDefinitions
import com.dickow.chortlin.testmodule.kotlin.KotlinInteractionDefinitions
import com.dickow.chortlin.testmodule.kotlin.KotlinSinkChannel
import kotlin.test.Test
import kotlin.test.assertTrue

class KotlinScenarioTest4 {

    @Test
    fun `create a sequential Interaction involving three methods`() {
        val sinkChannel = KotlinSinkChannel()

        val choreography = Chortlin.choreography()
                .onTrigger(KotlinEndpointDefinitions::class.java, "endpointWithStringInput", KotlinEndpointDefinitions::endpointWithStringInput)
                .mapInputTo { str -> str }
                .processWith { s -> s }
                .thenInteractWith(
                        Chortlin.interaction()
                                .onInteraction(
                                        KotlinInteractionDefinitions::class.java,
                                        "interaction",
                                        KotlinInteractionDefinitions::interaction)
                                .mapTo { s -> s }
                                .processWith { s -> s }
                                .thenInteractWith(
                                        Chortlin.interaction()
                                                .onInteraction(
                                                        KotlinInteractionDefinitions::class.java,
                                                        "interaction",
                                                        KotlinInteractionDefinitions::interaction)
                                                .mapTo { s -> s }
                                                .processWith { s -> s }
                                                .end())
                                .configureChannel(sinkChannel))
                .configureChannel(sinkChannel)
        assertTrue(choreography is Trigger)
    }
}