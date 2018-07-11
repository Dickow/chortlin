package com.dickow.chortlin.core.api.interfaces.trigger

interface ITriggerMapAPI {
    fun <TMapped> mapInputTo(mapper: () -> TMapped): ITriggerProcessAPI<TMapped>
}

interface ITriggerMapAPI1<T1> {
    fun <TMapped> mapInputTo(mapper: (T1) -> TMapped): ITriggerProcessAPI<TMapped>
}

interface ITriggerMapAPI2<T1, T2> {
    fun <TMapped> mapInputTo(mapper: (T1, T2) -> TMapped): ITriggerProcessAPI<TMapped>
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