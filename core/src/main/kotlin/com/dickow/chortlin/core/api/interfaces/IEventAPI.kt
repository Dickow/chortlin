package com.dickow.chortlin.core.api.interfaces

import com.dickow.chortlin.core.event.IEvent

interface IEventAPI<TEvent : IEvent> {
    fun <R> receivedOn(method: () -> R): IMapAPI<() -> TEvent, TEvent>
    fun <T1, R> receivedOn(method: (T1) -> R): IMapAPI<(T1) -> TEvent, TEvent>
    fun <T1, T2, R> receivedOn(method: (T1, T2) -> R): IMapAPI<(T1, T2) -> TEvent, TEvent>
    fun <T1, T2, T3, R> receivedOn(method: (T1, T2, T3) -> R): IMapAPI<(T1, T2, T3) -> TEvent, TEvent>
    fun <T1, T2, T3, T4, R> receivedOn(method: (T1, T2, T3, T4) -> R): IMapAPI<(T1, T2, T3, T4) -> TEvent, TEvent>
    fun <T1, T2, T3, T4, T5, R> receivedOn(method: (T1, T2, T3, T4, T5) -> R): IMapAPI<(T1, T2, T3, T4, T5) -> TEvent, TEvent>
    fun <T1, T2, T3, T4, T5, T6, R> receivedOn(method: (T1, T2, T3, T4, T5, T6) -> R): IMapAPI<(T1, T2, T3, T4, T5, T6) -> TEvent, TEvent>
}