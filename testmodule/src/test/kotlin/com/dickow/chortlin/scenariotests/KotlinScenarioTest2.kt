package com.dickow.chortlin.scenariotests

import com.dickow.chortlin.core.Chortlin
import com.dickow.chortlin.core.message.Channel
import com.dickow.chortlin.core.message.IMessage
import com.dickow.chortlin.testmodule.kotlin.scenario2.*
import kotlin.test.Test

class KotlinScenarioTest2 {

    @Test
    fun `setup two regular Java methods and annotate one of them with endpoint`() {
        val id = 100L
        val name = "Test man"
        val isAdmin = false

        Chortlin.choreography()
                .onTrigger(
                        Scenario2KotlinTriggerEndpoint::class.java,
                        "reduceRightsForUser",
                        Scenario2KotlinTriggerEndpoint::reduceRightsForUser)
                .handleWith(Scenario2KotlinTriggerHandler())
                .addInteraction(
                        Scenario2KotlinInputTransformer(),
                        Chortlin.interaction()
                                .onInteraction(
                                        Scenario2KotlinInteractionEndpoint::class.java,
                                        "revokeRights",
                                        Scenario2KotlinInteractionEndpoint::revokeRights)
                                .handleWith(Scenario2KotlinInteractionHandler(id))
                                .finish(InMemoryChannel())
                ).finish()

        Scenario2KotlinTriggerEndpoint().reduceRightsForUser(id, name, isAdmin)
    }

    class InMemoryChannel : Channel<Int> {
        override fun send(message: IMessage<Int>) {
            Scenario2KotlinInteractionEndpoint().revokeRights(message.getPayload())
        }

    }
}