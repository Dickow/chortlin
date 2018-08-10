package com.dickow.chortlin.core

import com.dickow.chortlin.core.api.endpoint.Endpoint
import com.dickow.chortlin.core.api.implementations.interaction.InteractionEndpointAPI
import com.dickow.chortlin.core.api.implementations.trigger.TriggerEndpointAPI
import com.dickow.chortlin.core.configuration.ChortlinConfiguration
import com.dickow.chortlin.core.configuration.ChortlinStepConfiguration
import com.dickow.chortlin.core.configuration.Subscriber
import com.dickow.chortlin.core.configuration.lookup.InMemoryLookup
import com.dickow.chortlin.core.configuration.lookup.Lookup

class Chortlin(private val lookup: Lookup, private val subscriber: Subscriber) {
    companion object Factory {
        private var chortlin: Chortlin? = null

        @JvmStatic
        @Synchronized
        fun get(): Chortlin {
            if (chortlin == null) {
                val lookup = InMemoryLookup()
                chortlin = Chortlin(lookup, ChortlinStepConfiguration(lookup))
            }

            return chortlin as Chortlin
        }

        @JvmStatic
        @Synchronized
        fun getNew(): Chortlin {
            val lookup = InMemoryLookup()
            chortlin = Chortlin(lookup, ChortlinStepConfiguration(lookup))
            return chortlin as Chortlin
        }
    }

    fun choreography(): com.dickow.chortlin.core.api.interfaces.trigger.TriggerEndpointAPI {
        return TriggerEndpointAPI(subscriber)
    }

    fun interaction(): com.dickow.chortlin.core.api.interfaces.interaction.InteractionEndpointAPI {
        return InteractionEndpointAPI(subscriber)
    }

    fun lookupConfiguration(rootKey: Int, endpoint: Endpoint): ChortlinConfiguration? {
        return lookup.lookup(rootKey, endpoint.hashCode())
    }

    fun lookupConfiguration(root: Endpoint, endpoint: Endpoint): ChortlinConfiguration? {
        return lookup.lookup(root.hashCode(), endpoint.hashCode())
    }
}

