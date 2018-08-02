package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerAPI
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerProcessAPI
import com.dickow.chortlin.core.configuration.process.Processor1
import com.dickow.chortlin.core.configuration.trigger.TriggerBuilder

class TriggerProcessAPI<TInput> constructor(private val definition: TriggerBuilder) : ITriggerProcessAPI<TInput> {
    override fun <TReturnMsg> processWith(processor: (TInput) -> TReturnMsg): ITriggerAPI {
        definition.processor = Processor1(processor)
        return TriggerAPI(definition)
    }

}