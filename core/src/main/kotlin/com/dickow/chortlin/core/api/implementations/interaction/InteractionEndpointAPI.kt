package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionEndpointAPI
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionMapAPI1
import com.dickow.chortlin.core.configuration.interaction.InteractionBuilder

class InteractionEndpointAPI : IInteractionEndpointAPI {
    override fun <TClass, T1, R> onInteraction(endpoint: (TClass, T1) -> R): IInteractionMapAPI1<T1> {
        val interactionDefinition = InteractionBuilder()
        interactionDefinition.endpoint = endpoint
        return MapAPI1(interactionDefinition)
    }
}