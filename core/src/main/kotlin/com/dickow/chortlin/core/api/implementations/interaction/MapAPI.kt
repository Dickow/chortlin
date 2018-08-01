package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionMapAPI
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionProcessAPI
import com.dickow.chortlin.core.configuration.interaction.InteractionDefinition
import com.dickow.chortlin.core.configuration.map.Mapper

class MapAPI constructor(private val interactionDefinition: InteractionDefinition) : IInteractionMapAPI {

    override fun <TMapped> mapTo(mapper: () -> TMapped): IInteractionProcessAPI<TMapped> {
        interactionDefinition.mapper = Mapper(mapper)
        return InteractionProcessAPI(interactionDefinition)
    }
}