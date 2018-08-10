package com.dickow.chortlin.core.api.interfaces.interaction

import com.dickow.chortlin.core.message.Message

interface InteractionEndpointAPI {
    fun <TClass, T1, R> onInteraction(
            clazz: Class<TClass>,
            name: String,
            endpoint: (TClass, Message<T1>) -> R)
            : InteractionMapAPI1<T1>
}