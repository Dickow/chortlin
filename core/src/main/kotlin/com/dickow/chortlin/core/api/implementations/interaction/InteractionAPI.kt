package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionAPI
import com.dickow.chortlin.core.configuration.interaction.Interaction
import com.dickow.chortlin.core.configuration.interaction.InteractionBuilder

class InteractionAPI constructor(private val interactionBuilder: InteractionBuilder) : IInteractionAPI {

    override fun thenInteractWith(interactions: Collection<Interaction>): InteractionBuilder {
        interactionBuilder.interactions = interactions
        return interactionBuilder
    }

    override fun end(): Interaction {
        interactionBuilder.interactions = emptyList()
        return interactionBuilder.noChannel()
    }

    override fun thenInteractWith(interaction: Interaction): InteractionBuilder {
        interactionBuilder.interactions = listOf(interaction)
        return interactionBuilder
    }
}