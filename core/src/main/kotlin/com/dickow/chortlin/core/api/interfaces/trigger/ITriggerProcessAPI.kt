package com.dickow.chortlin.core.api.interfaces.trigger

import com.dickow.chortlin.core.message.IMessage

interface ITriggerProcessAPI<TInput> {
    fun <TReturnMsg : IMessage> processWith(processor: (TInput) -> TReturnMsg): ITriggerAPI
}