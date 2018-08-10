package com.dickow.chortlin.core.configuration.trigger

import com.dickow.chortlin.core.api.endpoint.Endpoint
import com.dickow.chortlin.core.configuration.AbstractConfiguration
import com.dickow.chortlin.core.configuration.ChortlinConfiguration
import com.dickow.chortlin.core.configuration.map.IMapper
import com.dickow.chortlin.core.configuration.process.IProcessor
import com.dickow.chortlin.core.continuation.Continuation
import java.util.*

class Trigger constructor(
        endpoint: Endpoint,
        mapper: IMapper,
        processor: IProcessor,
        continuations: Collection<Continuation>) :
        AbstractConfiguration(endpoint, mapper, processor, continuations), ChortlinConfiguration {

    override fun equals(other: Any?): Boolean {
        if (other is Trigger) {
            return endpoint == other.endpoint
        }

        return false
    }

    override fun hashCode(): Int {
        return Objects.hashCode(endpoint)
    }
}