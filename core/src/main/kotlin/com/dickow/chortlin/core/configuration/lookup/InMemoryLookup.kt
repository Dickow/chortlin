package com.dickow.chortlin.core.configuration.lookup

import com.dickow.chortlin.core.configuration.ChortlinConfiguration
import com.dickow.chortlin.core.configuration.trigger.Trigger
import java.util.*

class InMemoryLookup : ILookup {

    private val lookupStore: MutableMap<Int, ChortlinConfiguration> = hashMapOf()

    override fun lookup(key: Any): ChortlinConfiguration? {
        return lookupStore[Objects.hashCode(key)]
    }

    override fun add(config: ChortlinConfiguration) {
        lookupStore.putIfAbsent(config.hashCode(), config)
        when (config) {
            is Trigger -> {
                config.interactions.forEach { lookupStore.putIfAbsent(it.hashCode(), it) }
            }
        }
    }
}