package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionAPI
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionProcessAPI
import com.dickow.chortlin.core.intercept.builders.IReceiveInterceptorBuilder
import com.dickow.chortlin.core.message.IMessage

class InteractionProcessAPI<TInput>
constructor(
        private val endpoint: Any,
        private val mapper: Any,
        private val receiveInterceptorBuilder: IReceiveInterceptorBuilder<TInput>
) : IInteractionProcessAPI<TInput> {

    override fun <TReturnMsg : IMessage> processWith(processor: (TInput) -> TReturnMsg): IInteractionAPI {
        receiveInterceptorBuilder.setProcessor(processor)
        return InteractionAPI(endpoint, mapper, this)
    }
}