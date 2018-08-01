package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerAPI
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerProcessAPI
import com.dickow.chortlin.core.configuration.Definition

class TriggerProcessAPI<TInput> constructor(private val definition: Definition) : ITriggerProcessAPI<TInput> {
    override fun <TReturnMsg> processWith(processor: (TInput) -> TReturnMsg): ITriggerAPI {
        definition.processor = processor
        return TriggerAPI(definition)
    }

}