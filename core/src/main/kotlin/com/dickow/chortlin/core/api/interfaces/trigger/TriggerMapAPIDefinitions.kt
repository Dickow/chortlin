package com.dickow.chortlin.core.api.interfaces.trigger

import com.dickow.chortlin.core.handlers.*

interface ITriggerMapAPI {
    fun <TMapped> mapInputTo(mapper: () -> TMapped): TriggerProcessAPI<TMapped>
    fun <TMapped, R> handleWith(handler: IHandler<TMapped, R>): TriggerAPI<R>
}

interface ITriggerMapAPI1<T1> {
    fun <TMapped> mapInputTo(mapper: (T1) -> TMapped): TriggerProcessAPI<TMapped>
    fun <T1, TMapped, R> handleWith(handler: IHandler1<T1, TMapped, R>): TriggerAPI<R>
}

interface ITriggerMapAPI2<T1, T2> {
    fun <TMapped> mapInputTo(mapper: (T1, T2) -> TMapped): TriggerProcessAPI<TMapped>
    fun <T1, T2, TMapped, R> handleWith(handler: IHandler2<T1, T2, TMapped, R>): TriggerAPI<R>
}

interface ITriggerMapAPI3<T1, T2, T3> {
    fun <TMapped> mapInputTo(mapper: (T1, T2, T3) -> TMapped): TriggerProcessAPI<TMapped>
    fun <T1, T2, T3, TMapped, R> handleWith(handler: IHandler3<T1, T2, T3, TMapped, R>): TriggerAPI<R>
}

interface ITriggerMapAPI4<T1, T2, T3, T4> {
    fun <TMapped> mapInputTo(mapper: (T1, T2, T3, T4) -> TMapped): TriggerProcessAPI<TMapped>
    fun <T1, T2, T3, T4, TMapped, R> handleWith(handler: IHandler4<T1, T2, T3, T4, TMapped, R>): TriggerAPI<R>
}

interface ITriggerMapAPI5<T1, T2, T3, T4, T5> {
    fun <TMapped> mapInputTo(mapper: (T1, T2, T3, T4, T5) -> TMapped): TriggerProcessAPI<TMapped>
    fun <T1, T2, T3, T4, T5, TMapped, R> handleWith(handler: IHandler5<T1, T2, T3, T4, T5, TMapped, R>): TriggerAPI<R>
}

interface ITriggerMapAPI6<T1, T2, T3, T4, T5, T6> {
    fun <TMapped> mapInputTo(mapper: (T1, T2, T3, T4, T5, T6) -> TMapped): TriggerProcessAPI<TMapped>
    fun <T1, T2, T3, T4, T5, T6, TMapped, R> handleWith(handler: IHandler6<T1, T2, T3, T4, T5, T6, TMapped, R>): TriggerAPI<R>
}