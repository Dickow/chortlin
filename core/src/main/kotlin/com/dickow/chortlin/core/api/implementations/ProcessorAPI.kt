package com.dickow.chortlin.core.api.implementations

import com.dickow.chortlin.core.api.interfaces.IMessageAPI
import com.dickow.chortlin.core.api.interfaces.IProcessorAPI
import com.dickow.chortlin.core.message.IMessage

class ProcessorAPI<TMsg : IMessage> : IProcessorAPI<TMsg> {
    override fun <TReturnMsg : IMessage> processWith(processor: (TMsg) -> TReturnMsg): IMessageAPI<TReturnMsg> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun processWithAndEnd(processor: (TMsg) -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}