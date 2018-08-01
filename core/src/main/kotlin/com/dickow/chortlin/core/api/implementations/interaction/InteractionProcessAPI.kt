package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionAPI
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionProcessAPI
import com.dickow.chortlin.core.configuration.interaction.InteractionDefinition

class InteractionProcessAPI<TInput>
constructor(private val interactionDefinition: InteractionDefinition) : IInteractionProcessAPI<TInput> {

    override fun <TReturnMsg> processWith(processor: (TInput) -> TReturnMsg): IInteractionAPI {
        interactionDefinition.processor = processor
        return InteractionAPI(interactionDefinition)
    }
}