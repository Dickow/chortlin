package com.dickow.chortlin.core.configuration.trigger

import com.dickow.chortlin.core.api.endpoint.Endpoint
import com.dickow.chortlin.core.configuration.ChortlinConfiguration
import com.dickow.chortlin.core.configuration.map.IMapper
import com.dickow.chortlin.core.configuration.process.IProcessor
import com.dickow.chortlin.core.continuation.Continuation
import java.util.*

class Trigger constructor(
        internal val endpoint: Endpoint,
        internal val mapper: IMapper,
        internal val processor: IProcessor,
        internal val continuations: Collection<Continuation>) : ChortlinConfiguration {

    override fun getEndpoint(): Endpoint {
        return endpoint
    }

    override fun applyTo() {
        continueChoreography(processor.process(arrayOf(mapper.map(emptyArray()))))
    }

    override fun applyTo(args: Array<Any?>) {
        continueChoreography(processor.process(arrayOf(mapper.map(args))))
    }

    override fun equals(other: Any?): Boolean {
        if (other is Trigger) {
            return endpoint == other.endpoint
        }

        return false
    }

    override fun hashCode(): Int {
        return Objects.hashCode(endpoint)
    }

    private fun continueChoreography(input: Any?) {
        continuations.forEach { continuation -> continuation.continueToNext(input) }
    }
}