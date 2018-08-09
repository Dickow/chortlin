package com.dickow.chortlin.core.api.interfaces.interaction

import com.dickow.chortlin.core.handlers.IHandler1

interface InteractionMapAPI1<T1> {
    fun <TMapped> mapTo(mapper: (T1) -> TMapped): InteractionProcessAPI<T1, TMapped>
    fun <T1, TMapped, R> handleWith(handler: IHandler1<T1, TMapped, R>): InteractionAPI<T1, R>
}