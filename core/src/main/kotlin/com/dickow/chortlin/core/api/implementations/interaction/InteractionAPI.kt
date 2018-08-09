package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionAPI
import com.dickow.chortlin.core.configuration.interaction.Interaction
import com.dickow.chortlin.core.configuration.interaction.InteractionBuilder
import com.dickow.chortlin.core.continuation.ChortlinContinuation
import com.dickow.chortlin.core.continuation.Transform
import com.dickow.chortlin.core.message.Channel

class InteractionAPI<TIn, TProcessed> constructor(
        private val interactionBuilder: InteractionBuilder) : IInteractionAPI<TIn, TProcessed> {

    override fun <TOut> addInteraction(
            transform: Transform<TProcessed, TOut>,
            interaction: Interaction<TOut>): IInteractionAPI<TIn, TProcessed> {
        val continuation = ChortlinContinuation(transform, interaction)
        interactionBuilder.addContinuation(continuation)
        return this
    }

    override fun finish(channel: Channel<TIn>): Interaction<TIn> {
        return interactionBuilder.build(channel)
    }
}