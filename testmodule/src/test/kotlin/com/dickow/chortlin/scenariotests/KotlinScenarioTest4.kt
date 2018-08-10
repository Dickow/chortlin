package com.dickow.chortlin.scenariotests

import com.dickow.chortlin.core.Chortlin
import com.dickow.chortlin.testmodule.kotlin.KotlinEndpointDefinitions
import com.dickow.chortlin.testmodule.kotlin.KotlinInteractionDefinitions
import com.dickow.chortlin.testmodule.kotlin.KotlinNoTransform
import com.dickow.chortlin.testmodule.kotlin.KotlinSinkChannel
import kotlin.test.Test

class KotlinScenarioTest4 {

    @Test
    fun `create a sequential Interaction involving three methods`() {
        val sinkChannel = KotlinSinkChannel<String>()

        val chortlin = Chortlin.getNew()

        chortlin.choreography()
                .onTrigger(
                        KotlinEndpointDefinitions::class.java,
                        "endpointWithStringInput",
                        KotlinEndpointDefinitions::endpointWithStringInput)
                .mapInputTo { str -> str }
                .processWith { s -> s }
                .addInteraction(
                        KotlinNoTransform(),
                        chortlin.interaction()
                                .onInteraction(
                                        KotlinInteractionDefinitions::class.java,
                                        "interaction",
                                        KotlinInteractionDefinitions::interaction)
                                .mapTo { s -> s }
                                .processWith { s -> s }
                                .addInteraction(
                                        KotlinNoTransform(),
                                        chortlin.interaction()
                                                .onInteraction(
                                                        KotlinInteractionDefinitions::class.java,
                                                        "interaction",
                                                        KotlinInteractionDefinitions::interaction)
                                                .mapTo { s -> s }
                                                .processWith { s -> s }
                                                .finish(sinkChannel))
                                .finish(sinkChannel))
                .finish()
    }
}