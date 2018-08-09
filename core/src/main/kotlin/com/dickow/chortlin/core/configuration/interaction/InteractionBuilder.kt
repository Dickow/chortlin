package com.dickow.chortlin.core.configuration.interaction

import com.dickow.chortlin.core.configuration.DefinitionBuilder
import com.dickow.chortlin.core.continuation.Continuation
import com.dickow.chortlin.core.message.Channel

class InteractionBuilder : DefinitionBuilder() {
    private val continuations: MutableCollection<Continuation> = mutableListOf()

    override fun addContinuation(continuation: Continuation) {
        continuations.add(continuation)
    }

    fun <TMsg> build(channel: Channel<TMsg>): Interaction<TMsg> {
        return Interaction(endpoint, mapper, processor, channel, continuations)
    }
}