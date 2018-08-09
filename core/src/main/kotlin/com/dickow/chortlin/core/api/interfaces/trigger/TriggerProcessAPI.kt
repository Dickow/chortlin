package com.dickow.chortlin.core.api.interfaces.trigger

interface TriggerProcessAPI<TInput> {
    fun <TReturnMsg> processWith(processor: (TInput) -> TReturnMsg): TriggerAPI<TReturnMsg>
}