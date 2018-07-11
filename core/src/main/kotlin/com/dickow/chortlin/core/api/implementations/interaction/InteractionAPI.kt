package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionAPI
import com.dickow.chortlin.core.interaction.Interaction
import com.dickow.chortlin.core.interaction.InteractionDefinition

class InteractionAPI<TInput> constructor(
        private val endpoint: Any,
        private val mapper: Any,
        private val process: InteractionProcessAPI<TInput>
) : IInteractionAPI {
    override fun thenInteractWith(interactions: List<Interaction>): InteractionDefinition {
        return InteractionDefinition(endpoint, mapper, process, interactions)
    }

    override fun end(): InteractionDefinition {
        return InteractionDefinition(endpoint, mapper, process, emptyList())
    }

    override fun thenInteractWith(interaction: Interaction): InteractionDefinition {
        return InteractionDefinition(endpoint, mapper, process, listOf(interaction))
    }
}