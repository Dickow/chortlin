package com.dickow.chortlin.core.api.interfaces.interaction

import com.dickow.chortlin.core.handlers.IHandler
import com.dickow.chortlin.core.handlers.IHandler1
import com.dickow.chortlin.core.handlers.IHandler2

interface IInteractionMapAPI {
    fun <TMapped> mapTo(mapper: () -> TMapped): IInteractionProcessAPI<TMapped>
    fun <TMapped, R> handleWith(handler: IHandler<TMapped, R>): IInteractionAPI
}

interface IInteractionMapAPI1<T1> {
    fun <TMapped> mapTo(mapper: (T1) -> TMapped): IInteractionProcessAPI<TMapped>
    fun <T1, TMapped, R> handleWith(handler: IHandler1<T1, TMapped, R>): IInteractionAPI
}

interface IInteractionMapAPI2<T1, T2> {
    fun <TMapped> mapTo(mapper: (T1, T2) -> TMapped): IInteractionProcessAPI<TMapped>
    fun <T1, T2, TMapped, R> handleWith(handler: IHandler2<T1, T2, TMapped, R>): IInteractionAPI
}

interface IInteractionMapAPI3<T1, T2, T3> {
    fun <TMapped> mapTo(mapper: (T1, T2, T3) -> TMapped): IInteractionProcessAPI<TMapped>
}

interface IInteractionMapAPI4<T1, T2, T3, T4> {
    fun <TMapped> mapTo(mapper: (T1, T2, T3, T4) -> TMapped): IInteractionProcessAPI<TMapped>
}

interface IInteractionMapAPI5<T1, T2, T3, T4, T5> {
    fun <TMapped> mapTo(mapper: (T1, T2, T3, T4, T5) -> TMapped): IInteractionProcessAPI<TMapped>
}

interface IInteractionMapAPI6<T1, T2, T3, T4, T5, T6> {
    fun <TMapped> mapTo(mapper: (T1, T2, T3, T4, T5, T6) -> TMapped): IInteractionProcessAPI<TMapped>
}