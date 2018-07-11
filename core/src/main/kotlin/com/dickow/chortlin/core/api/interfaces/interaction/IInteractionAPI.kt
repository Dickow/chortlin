package com.dickow.chortlin.core.api.interfaces.interaction

import com.dickow.chortlin.core.interaction.Interaction
import com.dickow.chortlin.core.interaction.InteractionDefinition

interface IInteractionAPI {
    fun thenInteractWith(interaction: Interaction): InteractionDefinition
    fun thenInteractWith(interactions: List<Interaction>): InteractionDefinition
    fun end(): InteractionDefinition
}