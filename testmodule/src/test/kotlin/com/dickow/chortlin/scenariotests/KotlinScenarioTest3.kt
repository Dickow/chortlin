package com.dickow.chortlin.scenariotests

import com.dickow.chortlin.core.Chortlin
import com.dickow.chortlin.core.continuation.NoTransform
import com.dickow.chortlin.core.message.Message
import com.dickow.chortlin.testmodule.kotlin.KotlinSinkChannel
import com.dickow.chortlin.testmodule.kotlin.scenario3.Scenario3KotlinInteractionEndpoint
import com.dickow.chortlin.testmodule.kotlin.scenario3.Scenario3KotlinInteractionHandler
import com.dickow.chortlin.testmodule.kotlin.scenario3.Scenario3KotlinTriggerEndpoint
import com.dickow.chortlin.testmodule.kotlin.scenario3.Scenario3KotlinTriggerHandler
import kotlin.test.Test
import kotlin.test.assertFails

class KotlinScenarioTest3 {

    @Test
    fun `Setup two regular Java methods and annotate one of them with endpoint`() {
        Chortlin.getNew().choreography()
                .onTrigger(Scenario3KotlinTriggerEndpoint::class.java,
                        "endpoint",
                        Scenario3KotlinTriggerEndpoint::endpoint)
                .handleWith(Scenario3KotlinTriggerHandler())
                .addInteraction(
                        NoTransform(),
                        Chortlin.get().interaction()
                                .onInteraction(
                                        Scenario3KotlinInteractionEndpoint::class.java,
                                        "deleteUser",
                                        Scenario3KotlinInteractionEndpoint::deleteUser)
                                .handleWith(Scenario3KotlinInteractionHandler())
                                .finish(KotlinSinkChannel()))
                .finish()
        val message = Message<Int>()
        message.payload = 100
        assertFails { Scenario3KotlinInteractionEndpoint().deleteUser(message) }
    }
}