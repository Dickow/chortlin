package com.dickow.chortlin.core.api.interfaces.interaction

interface InteractionProcessAPI<TIn, TInput> {
    fun <TReturnMsg> processWith(processor: (TInput) -> TReturnMsg): InteractionAPI<TIn, TReturnMsg>
}