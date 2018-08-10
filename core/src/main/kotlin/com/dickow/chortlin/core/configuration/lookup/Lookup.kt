package com.dickow.chortlin.core.configuration.lookup

import com.dickow.chortlin.core.api.endpoint.Endpoint
import com.dickow.chortlin.core.configuration.ChortlinConfiguration

interface Lookup {
    fun lookup(rootKey: Endpoint, key: Endpoint): ChortlinConfiguration?
    fun lookup(rootKey: Int, key: Int): ChortlinConfiguration?
    fun add(config: ChortlinConfiguration)
}