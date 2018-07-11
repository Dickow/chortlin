package com.dickow.chortlin.core.api.interfaces.interaction

interface IInteractionEndpointAPI {
    fun <TClass, T1, R> onInteraction(endpoint: (TClass, T1) -> R): IInteractionMapAPI1<T1>
}