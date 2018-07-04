package com.dickow.chortlin.core.api.implementations

import com.dickow.chortlin.core.api.interfaces.IMessageAPI
import com.dickow.chortlin.core.api.interfaces.IProcessorAPI
import com.dickow.chortlin.core.message.IMessage

class ProcessorAPI<TInput> : IProcessorAPI<TInput> {
    override fun <TReturnMsg : IMessage> processWith(processor: (TInput) -> TReturnMsg): IMessageAPI<TReturnMsg> {
        return MessageAPI()
    }

    override fun processWithAndEnd(processor: (TInput) -> Any) {

    }


}