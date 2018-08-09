package com.dickow.chortlin.core.api.interfaces.interaction

import com.dickow.chortlin.core.configuration.interaction.Interaction
import com.dickow.chortlin.core.continuation.Transform
import com.dickow.chortlin.core.message.Channel

interface InteractionAPI<TIn, TProcessed> {
    fun <TOut> addInteraction(
            transform: Transform<TProcessed, TOut>,
            interaction: Interaction<TOut>): InteractionAPI<TIn, TProcessed>

    fun finish(channel: Channel<TIn>): Interaction<TIn>
}