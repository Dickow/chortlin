package com.dickow.chortlin.core.api.interfaces.interaction

interface IInteractionMapAPI {
    fun <TMapped> mapTo(mapper: () -> TMapped): IInteractionProcessAPI<TMapped>
}

interface IInteractionMapAPI1<T1> {
    fun <TMapped> mapTo(mapper: (T1) -> TMapped): IInteractionProcessAPI<TMapped>
}

interface IInteractionMapAPI2<T1, T2> {
    fun <TMapped> mapTo(mapper: (T1, T2) -> TMapped): IInteractionProcessAPI<TMapped>
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