package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.TriggerAPI
import com.dickow.chortlin.core.api.interfaces.trigger.TriggerProcessAPI
import com.dickow.chortlin.core.configuration.Subscriber
import com.dickow.chortlin.core.configuration.process.Processor1
import com.dickow.chortlin.core.configuration.trigger.TriggerBuilder

class TriggerProcessAPI<TInput> constructor(
        private val definition: TriggerBuilder, private val subscriber: Subscriber) : TriggerProcessAPI<TInput> {
    override fun <TReturnMsg> processWith(processor: (TInput) -> TReturnMsg): TriggerAPI<TReturnMsg> {
        definition.processor = Processor1(processor)
        return TriggerAPI(definition, subscriber)
    }

}