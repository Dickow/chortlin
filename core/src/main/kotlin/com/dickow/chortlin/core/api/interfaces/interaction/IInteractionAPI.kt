package com.dickow.chortlin.core.api.interfaces.interaction

import com.dickow.chortlin.core.configuration.interaction.Interaction
import com.dickow.chortlin.core.configuration.interaction.InteractionDefinition

interface IInteractionAPI {
    fun thenInteractWith(interaction: Interaction): InteractionDefinition
    fun thenInteractWith(interactions: Collection<Interaction>): InteractionDefinition
    fun end(): InteractionDefinition
}