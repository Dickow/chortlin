package com.dickow.chortlin.core.configuration

import com.dickow.chortlin.core.api.endpoint.Endpoint
import com.dickow.chortlin.core.configuration.map.IMapper
import com.dickow.chortlin.core.configuration.process.IProcessor
import com.dickow.chortlin.core.continuation.Accumulator
import com.dickow.chortlin.core.continuation.Continuation

abstract class AbstractConfiguration constructor(
        internal val endpoint: Endpoint,
        internal val mapper: IMapper,
        internal val processor: IProcessor,
        val continuations: Collection<Continuation>)
    : ChortlinConfiguration {
    override fun getNextSteps(): Collection<ChortlinConfiguration> {
        return continuations.map { it.getConfiguration() }
    }

    override fun getEndpoint(): Endpoint {
        return endpoint
    }

    override fun applyTo(args: Array<Any?>, accumulator: Accumulator) {
        val mappedValues = mapper.map(args)
        val processedValue = processor.process(mappedValues)
        continueChoreography(processedValue, accumulator)
    }

    override fun applyTo(accumulator: Accumulator) {
        val mappedValues = mapper.map(emptyArray())
        val processedValue = processor.process(mappedValues)
        continueChoreography(processedValue, accumulator)
    }

    protected fun continueChoreography(input: Any?, accumulator: Accumulator) {
        continuations.forEach { continuation -> continuation.continueToNext(input, accumulator) }
    }
}