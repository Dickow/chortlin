package com.dickow.chortlin.core.api.interfaces.interaction

interface InteractionEndpointAPI {
    fun <TClass, T1, R> onInteraction(clazz: Class<TClass>, name: String, endpoint: (TClass, T1) -> R): InteractionMapAPI1<T1>
}