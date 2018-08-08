package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.endpoint.Endpoint
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionEndpointAPI
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionMapAPI1
import com.dickow.chortlin.core.configuration.interaction.InteractionBuilder

class InteractionEndpointAPI : IInteractionEndpointAPI {

    override fun <TClass, T1, R> onInteraction(
            clazz: Class<TClass>, name: String, endpoint: (TClass, T1) -> R): IInteractionMapAPI1<T1> {
        val interactionDefinition = InteractionBuilder()
        interactionDefinition.endpoint = Endpoint(clazz, name)
        return MapAPI1(interactionDefinition)
    }
}