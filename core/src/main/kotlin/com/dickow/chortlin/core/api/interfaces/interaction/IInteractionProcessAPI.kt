package com.dickow.chortlin.core.api.interfaces.interaction

interface IInteractionProcessAPI<TIn, TInput> {
    fun <TReturnMsg> processWith(processor: (TInput) -> TReturnMsg): IInteractionAPI<TIn, TReturnMsg>
}