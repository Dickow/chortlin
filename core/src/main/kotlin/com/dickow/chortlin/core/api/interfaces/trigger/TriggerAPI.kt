package com.dickow.chortlin.core.api.interfaces.trigger

import com.dickow.chortlin.core.configuration.interaction.Interaction
import com.dickow.chortlin.core.configuration.trigger.Trigger
import com.dickow.chortlin.core.continuation.Transform

interface TriggerAPI<TProcessed> {
    fun <TOut> addInteraction(transform: Transform<TProcessed, TOut>, interaction: Interaction<TOut>): TriggerAPI<TProcessed>
    fun finish(): Trigger
}