package com.dickow.chortlin.core.api.interfaces

import com.dickow.chortlin.core.message.IMessage

interface IProcessorAPI<TMsg : IMessage> {
    fun <TReturnMsg : IMessage> processWith(processor: (TMsg) -> TReturnMsg): IMessageAPI<TReturnMsg>
    fun processWithAndEnd(processor: (TMsg) -> Unit)
}