package com.dickow.chortlin.core

import com.dickow.chortlin.core.api.endpoint.Endpoint
import com.dickow.chortlin.core.api.implementations.interaction.InteractionEndpointAPI
import com.dickow.chortlin.core.api.implementations.trigger.TriggerEndpointAPI
import com.dickow.chortlin.core.configuration.ChortlinConfiguration
import com.dickow.chortlin.core.configuration.ChortlinStepConfiguration
import com.dickow.chortlin.core.configuration.lookup.InMemoryLookup
import com.dickow.chortlin.core.configuration.lookup.Lookup

object Chortlin {
    private val lookup: Lookup = InMemoryLookup()
    private val subscriber = ChortlinStepConfiguration(lookup)

    fun choreography(): com.dickow.chortlin.core.api.interfaces.trigger.TriggerEndpointAPI {
        return TriggerEndpointAPI(subscriber)
    }

    fun interaction(): com.dickow.chortlin.core.api.interfaces.interaction.InteractionEndpointAPI {
        return InteractionEndpointAPI(subscriber)
    }

    fun lookupConfiguration(endpoint: Endpoint): ChortlinConfiguration? {
        return lookup.lookup(endpoint)
    }
}

