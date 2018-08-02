package com.dickow.chortlin.core.api.interfaces.trigger

import com.dickow.chortlin.core.configuration.ChortlinConfiguration
import com.dickow.chortlin.core.configuration.Definition
import com.dickow.chortlin.core.configuration.interaction.Interaction

interface ITriggerAPI {
    fun thenInteractWith(interaction: Interaction): Definition
    fun thenInteractWith(interactions: Collection<Interaction>): Definition
    fun end(): ChortlinConfiguration
}