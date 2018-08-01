package com.dickow.chortlin.core

import com.dickow.chortlin.core.api.implementations.interaction.InteractionEndpointAPI
import com.dickow.chortlin.core.api.implementations.trigger.TriggerEndpointAPI
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionEndpointAPI
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerEndpointAPI

object Chortlin {

    fun choreography(): ITriggerEndpointAPI {
        return TriggerEndpointAPI()
    }

    fun interaction(): IInteractionEndpointAPI {
        return InteractionEndpointAPI()
    }
}

