package com.dickow.chortlin.core.api.interfaces

interface IMapAPI {
    fun <TMapped> mapTo(mapper: () -> TMapped): IProcessorAPI<TMapped>
}

interface IMapAPI1<T1> {
    fun <TMapped> mapTo(mapper: (T1) -> TMapped): IProcessorAPI<TMapped>
}

interface IMapAPI2<T1, T2> {
    fun <TMapped> mapTo(mapper: (T1, T2) -> TMapped): IProcessorAPI<TMapped>
}

interface IMapAPI3<T1, T2, T3> {
    fun <TMapped> mapTo(mapper: (T1, T2, T3) -> TMapped): IProcessorAPI<TMapped>
}

interface IMapAPI4<T1, T2, T3, T4> {
    fun <TMapped> mapTo(mapper: (T1, T2, T3, T4) -> TMapped): IProcessorAPI<TMapped>
}

interface IMapAPI5<T1, T2, T3, T4, T5> {
    fun <TMapped> mapTo(mapper: (T1, T2, T3, T4, T5) -> TMapped): IProcessorAPI<TMapped>
}

interface IMapAPI6<T1, T2, T3, T4, T5, T6> {
    fun <TMapped> mapTo(mapper: (T1, T2, T3, T4, T5, T6) -> TMapped): IProcessorAPI<TMapped>
}