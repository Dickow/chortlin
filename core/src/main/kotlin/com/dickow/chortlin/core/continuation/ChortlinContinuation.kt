package com.dickow.chortlin.core.continuation

import com.dickow.chortlin.core.api.endpoint.Endpoint
import com.dickow.chortlin.core.configuration.ChortlinConfiguration
import com.dickow.chortlin.core.configuration.interaction.Interaction
import com.dickow.chortlin.core.message.SimpleSender

class ChortlinContinuation<in TIn, out TOut> constructor(
        private val transformer: Transform<TIn, TOut>,
        private val interaction: Interaction<TOut>) : Continuation {
    override fun getEndpoint(): Endpoint {
        return interaction.getEndpoint()
    }

    override fun getConfiguration(): ChortlinConfiguration {
        return interaction
    }

    @Suppress("UNCHECKED_CAST")
    override fun continueToNext(input: Any?) {
        val sender = SimpleSender(interaction.channel)
        sender.send(transformer.transform(input as TIn) as Any?)
    }
}