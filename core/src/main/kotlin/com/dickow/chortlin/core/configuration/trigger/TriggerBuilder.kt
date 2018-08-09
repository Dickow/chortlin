package com.dickow.chortlin.core.configuration.trigger

import com.dickow.chortlin.core.configuration.DefinitionBuilder
import com.dickow.chortlin.core.continuation.Continuation

class TriggerBuilder : DefinitionBuilder() {
    private val continuations: MutableCollection<Continuation> = mutableListOf()

    fun build(): Trigger {
        return Trigger(endpoint, mapper, processor, continuations)
    }

    override fun addContinuation(continuation: Continuation) {
        continuations.add(continuation)
    }
}