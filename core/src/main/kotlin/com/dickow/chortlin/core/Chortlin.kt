package com.dickow.chortlin.core

import com.dickow.chortlin.core.api.endpoint.Endpoint
import com.dickow.chortlin.core.api.implementations.interaction.InteractionEndpointAPI
import com.dickow.chortlin.core.api.implementations.trigger.TriggerEndpointAPI
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionEndpointAPI
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerEndpointAPI
import com.dickow.chortlin.core.configuration.ChortlinConfiguration
import com.dickow.chortlin.core.configuration.lookup.InMemoryLookup
import com.dickow.chortlin.core.configuration.lookup.Lookup

object Chortlin {
    private val lookup: Lookup = InMemoryLookup()

    fun addConfiguration(config: ChortlinConfiguration) {
        lookup.add(config)
    }

    fun lookupConfiguration(endpoint: Endpoint): ChortlinConfiguration? {
        return lookup.lookup(endpoint)
    }

    fun choreography(): ITriggerEndpointAPI {
        return TriggerEndpointAPI()
    }

    fun interaction(): IInteractionEndpointAPI {
        return InteractionEndpointAPI()
    }
}

