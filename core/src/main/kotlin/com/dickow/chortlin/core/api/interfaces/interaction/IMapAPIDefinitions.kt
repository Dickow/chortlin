package com.dickow.chortlin.core.api.interfaces.interaction

import com.dickow.chortlin.core.handlers.*

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
    fun <T1, T2, T3, TMapped, R> handleWith(handler: IHandler3<T1, T2, T3, TMapped, R>): IInteractionAPI
}

interface IInteractionMapAPI4<T1, T2, T3, T4> {
    fun <TMapped> mapTo(mapper: (T1, T2, T3, T4) -> TMapped): IInteractionProcessAPI<TMapped>
    fun <T1, T2, T3, T4, TMapped, R> handleWith(handler: IHandler4<T1, T2, T3, T4, TMapped, R>): IInteractionAPI
}

interface IInteractionMapAPI5<T1, T2, T3, T4, T5> {
    fun <TMapped> mapTo(mapper: (T1, T2, T3, T4, T5) -> TMapped): IInteractionProcessAPI<TMapped>
    fun <T1, T2, T3, T4, T5, TMapped, R> handleWith(handler: IHandler5<T1, T2, T3, T4, T5, TMapped, R>): IInteractionAPI
}

interface IInteractionMapAPI6<T1, T2, T3, T4, T5, T6> {
    fun <TMapped> mapTo(mapper: (T1, T2, T3, T4, T5, T6) -> TMapped): IInteractionProcessAPI<TMapped>
    fun <T1, T2, T3, T4, T5, T6, TMapped, R> handleWith(handler: IHandler6<T1, T2, T3, T4, T5, T6, TMapped, R>): IInteractionAPI
}