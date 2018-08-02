package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerAPI
import com.dickow.chortlin.core.configuration.Definition
import com.dickow.chortlin.core.configuration.interaction.Interaction
import com.dickow.chortlin.core.configuration.trigger.Trigger
import com.dickow.chortlin.core.configuration.trigger.TriggerBuilder

class TriggerAPI constructor(private val definition: TriggerBuilder) : ITriggerAPI {

    override fun thenInteractWith(interaction: Interaction): Definition {
        definition.interactions = listOf(interaction)
        return definition
    }

    override fun thenInteractWith(interactions: Collection<Interaction>): Definition {
        definition.interactions = interactions
        return definition
    }

    override fun end(): Trigger {
        definition.interactions = emptyList()
        return definition.noChannel()
    }
}