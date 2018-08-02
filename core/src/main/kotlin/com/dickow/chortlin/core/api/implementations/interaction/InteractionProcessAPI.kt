package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionAPI
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionProcessAPI
import com.dickow.chortlin.core.configuration.interaction.InteractionBuilder
import com.dickow.chortlin.core.configuration.process.Processor1

class InteractionProcessAPI<TInput>
constructor(private val interactionBuilder: InteractionBuilder) : IInteractionProcessAPI<TInput> {

    override fun <TReturnMsg> processWith(processor: (TInput) -> TReturnMsg): IInteractionAPI {
        interactionBuilder.processor = Processor1(processor)
        return InteractionAPI(interactionBuilder)
    }
}