package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.interfaces.interaction.InteractionAPI
import com.dickow.chortlin.core.api.interfaces.interaction.InteractionProcessAPI
import com.dickow.chortlin.core.configuration.interaction.InteractionBuilder
import com.dickow.chortlin.core.configuration.process.Processor1

class InteractionProcessAPI<TIn, TInput>
constructor(private val interactionBuilder: InteractionBuilder) : InteractionProcessAPI<TIn, TInput> {

    override fun <TReturnMsg> processWith(processor: (TInput) -> TReturnMsg): InteractionAPI<TIn, TReturnMsg> {
        interactionBuilder.processor = Processor1(processor)
        return InteractionAPI(interactionBuilder)
    }
}