package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.interfaces.interaction.InteractionAPI
import com.dickow.chortlin.core.configuration.Subscriber
import com.dickow.chortlin.core.configuration.interaction.Interaction
import com.dickow.chortlin.core.configuration.interaction.InteractionBuilder
import com.dickow.chortlin.core.continuation.ChortlinContinuation
import com.dickow.chortlin.core.continuation.Transform
import com.dickow.chortlin.core.message.Channel

class InteractionAPI<TIn, TProcessed> constructor(
        private val interactionBuilder: InteractionBuilder, private val subscriber: Subscriber)
    : InteractionAPI<TIn, TProcessed> {

    override fun <TOut> addInteraction(
            transform: Transform<TProcessed, TOut>,
            interaction: Interaction<TOut>): InteractionAPI<TIn, TProcessed> {
        val continuation = ChortlinContinuation(transform, interaction)
        interactionBuilder.addContinuation(continuation)
        return this
    }

    override fun finish(channel: Channel<TIn>): Interaction<TIn> {
        val interaction = interactionBuilder.build(channel)
        subscriber.onInteractionCompleted(interaction)
        return interaction
    }
}