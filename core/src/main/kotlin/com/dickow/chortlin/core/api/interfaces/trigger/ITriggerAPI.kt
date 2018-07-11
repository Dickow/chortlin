package com.dickow.chortlin.core.api.interfaces.trigger

import com.dickow.chortlin.core.interaction.Interaction
import com.dickow.chortlin.core.trigger.Trigger

interface ITriggerAPI {
    fun thenInteractWith(interaction: Interaction): Trigger
    fun thenInteractWith(interactions: Collection<Interaction>): Trigger
    fun end(): Trigger
}