package com.dickow.chortlin.core.api.interfaces.interaction

import com.dickow.chortlin.core.handlers.IHandler1

interface IInteractionMapAPI1<T1> {
    fun <TMapped> mapTo(mapper: (T1) -> TMapped): IInteractionProcessAPI<T1, TMapped>
    fun <T1, TMapped, R> handleWith(handler: IHandler1<T1, TMapped, R>): IInteractionAPI<T1, R>
}