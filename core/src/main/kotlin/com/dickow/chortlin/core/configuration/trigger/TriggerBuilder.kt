package com.dickow.chortlin.core.configuration.trigger

import com.dickow.chortlin.core.Chortlin
import com.dickow.chortlin.core.configuration.DefinitionBuilder
import com.dickow.chortlin.core.continuation.Continuation

class TriggerBuilder : DefinitionBuilder() {
    private val continuations: MutableCollection<Continuation> = mutableListOf()

    fun build(): Trigger {
        val trigger = Trigger(endpoint, mapper, processor, continuations)
        Chortlin.addConfiguration(trigger)
        return trigger
    }

    override fun addContinuation(continuation: Continuation) {
        continuations.add(continuation)
    }
}