package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerAPI
import com.dickow.chortlin.core.configuration.Definition
import com.dickow.chortlin.core.configuration.interaction.Interaction

class TriggerAPI constructor(private val definition: Definition) : ITriggerAPI {
    override fun thenInteractWith(interaction: Interaction): Definition {
        definition.interactions = listOf(interaction)
        return definition
    }

    override fun thenInteractWith(interactions: Collection<Interaction>): Definition {
        definition.interactions = interactions
        return definition
    }

    override fun end(): Definition {
        definition.interactions = emptyList()
        return definition
    }
}