package com.dickow.chortlin.core.api.interfaces

import com.dickow.chortlin.core.message.IMessage

interface IProcessorAPI<TInput> {
    fun <TReturnMsg : IMessage> processWith(processor: (TInput) -> TReturnMsg): IMessageAPI<TReturnMsg>
    fun processWithAndEnd(processor: (TInput) -> Any)
}