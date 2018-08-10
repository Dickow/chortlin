package com.dickow.chortlin.core.configuration.lookup

import com.dickow.chortlin.core.api.endpoint.Endpoint
import com.dickow.chortlin.core.configuration.ChortlinConfiguration
import com.dickow.chortlin.core.configuration.trigger.Trigger

class InMemoryLookup : Lookup {
    private val lookupStore: MutableMap<Int, TreeNode<Int, ChortlinConfiguration>> = mutableMapOf()

    override fun lookup(rootKey: Int, key: Int): ChortlinConfiguration? {
        return lookupStore[rootKey]?.get(key)
    }

    override fun lookup(rootKey: Endpoint, key: Endpoint): ChortlinConfiguration? {
        return lookupStore[rootKey.hashCode()]?.get(key.hashCode())
    }

    override fun add(config: ChortlinConfiguration) {
        when (config) {
            is Trigger -> {
                val rootKey = config.getEndpoint().hashCode()
                if (lookupStore.containsKey(rootKey)) {
                    throw ChortlinConfigurationException("Endpoint already added: ${config.endpoint}")
                }

                lookupStore[rootKey] = buildNode(config)
            }
        }
    }

    private fun buildNode(config: ChortlinConfiguration): TreeNode<Int, ChortlinConfiguration> {
        val key = config.getEndpoint().hashCode()
        val children = config.getNextSteps().map { buildNode(it) }
        return TreeNode(key, config, children)
    }
}