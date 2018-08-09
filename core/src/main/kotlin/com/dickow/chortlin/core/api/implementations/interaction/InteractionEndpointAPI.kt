package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.endpoint.Endpoint
import com.dickow.chortlin.core.api.interfaces.interaction.InteractionEndpointAPI
import com.dickow.chortlin.core.api.interfaces.interaction.InteractionMapAPI1
import com.dickow.chortlin.core.configuration.Subscriber
import com.dickow.chortlin.core.configuration.interaction.InteractionBuilder

class InteractionEndpointAPI constructor(private val subscriber: Subscriber) : InteractionEndpointAPI {

    override fun <TClass, T1, R> onInteraction(
            clazz: Class<TClass>, name: String, endpoint: (TClass, T1) -> R): InteractionMapAPI1<T1> {
        val interactionDefinition = InteractionBuilder()
        interactionDefinition.endpoint = Endpoint(clazz, name)
        return MapAPI1(interactionDefinition, subscriber)
    }
}