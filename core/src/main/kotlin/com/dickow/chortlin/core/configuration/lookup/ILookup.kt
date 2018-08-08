package com.dickow.chortlin.core.configuration.lookup

import com.dickow.chortlin.core.api.endpoint.Endpoint
import com.dickow.chortlin.core.configuration.ChortlinConfiguration

interface ILookup {
    fun lookup(key: Endpoint): ChortlinConfiguration?
    fun add(config: ChortlinConfiguration)
}