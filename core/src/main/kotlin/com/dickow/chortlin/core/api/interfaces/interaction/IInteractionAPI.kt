package com.dickow.chortlin.core.api.interfaces.interaction

import com.dickow.chortlin.core.configuration.interaction.Interaction
import com.dickow.chortlin.core.configuration.interaction.InteractionBuilder

interface IInteractionAPI {
    fun thenInteractWith(interaction: Interaction): InteractionBuilder
    fun thenInteractWith(interactions: Collection<Interaction>): InteractionBuilder
    fun end(): Interaction
}