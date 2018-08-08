package com.dickow.chortlin.core.configuration.interaction

import com.dickow.chortlin.core.api.endpoint.Endpoint
import com.dickow.chortlin.core.configuration.ChortlinConfiguration
import com.dickow.chortlin.core.configuration.map.IMapper
import com.dickow.chortlin.core.configuration.process.IProcessor
import java.util.*

class Interaction constructor(
        internal val endpoint: Endpoint,
        internal val mapper: IMapper,
        internal val processor: IProcessor,
        internal val interactions: Collection<Interaction>) : ChortlinConfiguration {
    override fun getEndpoint(): Endpoint {
        return endpoint
    }

    override fun applyTo(args: Array<Any?>) {
        processor.process(arrayOf(mapper.map(args)))
    }

    override fun applyTo() {
        processor.process(arrayOf(mapper.map(emptyArray())))
    }

    override fun equals(other: Any?): Boolean {
        if (other is Interaction) {
            return endpoint == other.endpoint
        }

        return false
    }

    override fun hashCode(): Int {
        return Objects.hashCode(endpoint)
    }
}