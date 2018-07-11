package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerAPI
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerProcessAPI
import com.dickow.chortlin.core.message.IMessage

class TriggerProcessAPI<TInput> : ITriggerProcessAPI<TInput> {
    override fun <TReturnMsg : IMessage> processWith(processor: (TInput) -> TReturnMsg): ITriggerAPI {
        return TriggerAPI()
    }

}