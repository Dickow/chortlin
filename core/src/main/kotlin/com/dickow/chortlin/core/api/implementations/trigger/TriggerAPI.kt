package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerAPI
import com.dickow.chortlin.core.configuration.interaction.Interaction
import com.dickow.chortlin.core.configuration.trigger.Trigger
import com.dickow.chortlin.core.configuration.trigger.TriggerBuilder
import com.dickow.chortlin.core.continuation.ChortlinContinuation
import com.dickow.chortlin.core.continuation.Transform

class TriggerAPI<TProcessed> constructor(private val definition: TriggerBuilder) : ITriggerAPI<TProcessed> {
    override fun <TOut> addInteraction(
            transform: Transform<TProcessed, TOut>,
            interaction: Interaction<TOut>): ITriggerAPI<TProcessed> {
        val continuation = ChortlinContinuation(transform, interaction)
        definition.addContinuation(continuation)
        return this
    }

    override fun finish(): Trigger {
        return definition.build()
    }
}