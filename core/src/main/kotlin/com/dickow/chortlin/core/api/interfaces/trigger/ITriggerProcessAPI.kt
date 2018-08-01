package com.dickow.chortlin.core.api.interfaces.trigger

interface ITriggerProcessAPI<TInput> {
    fun <TReturnMsg> processWith(processor: (TInput) -> TReturnMsg): ITriggerAPI
}