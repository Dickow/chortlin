package com.dickow.chortlin.core.configuration.lookup

import com.dickow.chortlin.core.configuration.ChortlinConfiguration

interface ILookup {
    fun lookup(key: Any): ChortlinConfiguration?
    fun add(config: ChortlinConfiguration)
}