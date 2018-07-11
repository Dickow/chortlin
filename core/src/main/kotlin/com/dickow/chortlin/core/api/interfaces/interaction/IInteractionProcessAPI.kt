package com.dickow.chortlin.core.api.interfaces.interaction

import com.dickow.chortlin.core.message.IMessage

interface IInteractionProcessAPI<TInput> {
    fun <TReturnMsg : IMessage> processWith(processor: (TInput) -> TReturnMsg): IInteractionAPI
}