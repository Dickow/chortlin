package com.dickow.chortlin.core.api.interfaces

interface IEndpointAPI {
    fun <TClass, R> onTrigger(method: (TClass) -> R): IMapAPI
    fun <TClass, T1, R> onTrigger(method: (TClass, T1) -> R): IMapAPI1<T1>
    fun <TClass, T1, T2, R> onTrigger(method: (TClass, T1, T2) -> R): IMapAPI2<T1, T2>
    fun <TClass, T1, T2, T3, R> onTrigger(method: (TClass, T1, T2, T3) -> R): IMapAPI3<T1, T2, T3>
    fun <TClass, T1, T2, T3, T4, R> onTrigger(method: (TClass, T1, T2, T3, T4) -> R): IMapAPI4<T1, T2, T3, T4>
    fun <TClass, T1, T2, T3, T4, T5, R> onTrigger(method: (TClass, T1, T2, T3, T4, T5) -> R): IMapAPI5<T1, T2, T3, T4, T5>
    fun <TClass, T1, T2, T3, T4, T5, T6, R> onTrigger(method: (TClass, T1, T2, T3, T4, T5, T6) -> R): IMapAPI6<T1, T2, T3, T4, T5, T6>
}