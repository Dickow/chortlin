package com.dickow.chortlin.core.api.interfaces.interaction

interface IInteractionProcessAPI<TInput> {
    fun <TReturnMsg> processWith(processor: (TInput) -> TReturnMsg): IInteractionAPI
}