package com.dickow.chortlin.core.configuration

import com.dickow.chortlin.core.api.endpoint.Endpoint
import com.dickow.chortlin.core.configuration.map.IMapper
import com.dickow.chortlin.core.configuration.process.IProcessor
import com.dickow.chortlin.core.continuation.Continuation

abstract class DefinitionBuilder {
    internal lateinit var endpoint: Endpoint
    internal lateinit var mapper: IMapper
    internal lateinit var processor: IProcessor

    abstract fun addContinuation(continuation: Continuation)
}