package com.dickow.chortlin.core.continuation

import com.dickow.chortlin.core.api.endpoint.Endpoint
import com.dickow.chortlin.core.configuration.ChortlinConfiguration

interface Continuation {
    fun continueToNext(input: Any?, accumulator: Accumulator)
    fun getEndpoint(): Endpoint
    fun getConfiguration(): ChortlinConfiguration
}