package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionAPI
import com.dickow.chortlin.core.configuration.interaction.Interaction
import com.dickow.chortlin.core.configuration.interaction.InteractionDefinition

class InteractionAPI constructor(private val interactionDefinition: InteractionDefinition) : IInteractionAPI {
    override fun thenInteractWith(interactions: Collection<Interaction>): InteractionDefinition {
        interactionDefinition.interactions = interactions
        return interactionDefinition
    }

    override fun end(): InteractionDefinition {
        interactionDefinition.interactions = emptyList()
        return interactionDefinition
    }

    override fun thenInteractWith(interaction: Interaction): InteractionDefinition {
        interactionDefinition.interactions = listOf(interaction)
        return interactionDefinition
    }
}