package com.dickow.chortlin.core.configuration.lookup

import com.dickow.chortlin.core.api.endpoint.Endpoint
import com.dickow.chortlin.core.configuration.ChortlinConfiguration
import com.dickow.chortlin.core.configuration.trigger.Trigger

class InMemoryLookup : ILookup {

    private val lookupStore: MutableMap<Endpoint, ChortlinConfiguration> = hashMapOf()

    override fun lookup(key: Endpoint): ChortlinConfiguration? {
        return lookupStore[key]
    }

    override fun add(config: ChortlinConfiguration) {
        lookupStore.putIfAbsent(config.getEndpoint(), config)
        when (config) {
            is Trigger -> {
                config.interactions.forEach { lookupStore.putIfAbsent(it.getEndpoint(), it) }
            }
        }
    }
}