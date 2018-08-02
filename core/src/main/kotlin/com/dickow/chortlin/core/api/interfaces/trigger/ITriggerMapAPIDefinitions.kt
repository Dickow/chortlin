package com.dickow.chortlin.core.api.interfaces.trigger

import com.dickow.chortlin.core.handlers.IHandler
import com.dickow.chortlin.core.handlers.IHandler1
import com.dickow.chortlin.core.handlers.IHandler2

interface ITriggerMapAPI {
    fun <TMapped> mapInputTo(mapper: () -> TMapped): ITriggerProcessAPI<TMapped>
    fun <TMapped, R> handleWith(handler: IHandler<TMapped, R>): ITriggerAPI
}

interface ITriggerMapAPI1<T1> {
    fun <TMapped> mapInputTo(mapper: (T1) -> TMapped): ITriggerProcessAPI<TMapped>
    fun <T1, TMapped, R> handleWith(handler: IHandler1<T1, TMapped, R>): ITriggerAPI
}

interface ITriggerMapAPI2<T1, T2> {
    fun <TMapped> mapInputTo(mapper: (T1, T2) -> TMapped): ITriggerProcessAPI<TMapped>
    fun <T1, T2, TMapped, R> handleWith(handler: IHandler2<T1, T2, TMapped, R>): ITriggerAPI
}

interface ITriggerMapAPI3<T1, T2, T3> {
    fun <TMapped> mapInputTo(mapper: (T1, T2, T3) -> TMapped): ITriggerProcessAPI<TMapped>
}

interface ITriggerMapAPI4<T1, T2, T3, T4> {
    fun <TMapped> mapInputTo(mapper: (T1, T2, T3, T4) -> TMapped): ITriggerProcessAPI<TMapped>
}

interface ITriggerMapAPI5<T1, T2, T3, T4, T5> {
    fun <TMapped> mapInputTo(mapper: (T1, T2, T3, T4, T5) -> TMapped): ITriggerProcessAPI<TMapped>
}

interface ITriggerMapAPI6<T1, T2, T3, T4, T5, T6> {
    fun <TMapped> mapInputTo(mapper: (T1, T2, T3, T4, T5, T6) -> TMapped): ITriggerProcessAPI<TMapped>
}