package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.implementations.MapAPI1
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionEndpointAPI
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionMapAPI1

class InteractionEndpointAPI : IInteractionEndpointAPI {
    override fun <TClass, T1, R> onInteraction(endpoint: (TClass, T1) -> R): IInteractionMapAPI1<T1> {
        return MapAPI1(this, endpoint)
    }
}