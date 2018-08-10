package com.dickow.chortlin.core.configuration

import com.dickow.chortlin.core.api.endpoint.Endpoint
import com.dickow.chortlin.core.continuation.Accumulator

interface ChortlinConfiguration {
    fun getEndpoint(): Endpoint
    fun applyTo(args: Array<Any?>, accumulator: Accumulator)
    fun applyTo(accumulator: Accumulator)
    fun getNextSteps(): Collection<ChortlinConfiguration>
}