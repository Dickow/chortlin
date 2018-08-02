package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionMapAPI
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionProcessAPI
import com.dickow.chortlin.core.configuration.interaction.InteractionBuilder
import com.dickow.chortlin.core.configuration.map.Mapper

class MapAPI constructor(private val interactionBuilder: InteractionBuilder) : IInteractionMapAPI {

    override fun <TMapped> mapTo(mapper: () -> TMapped): IInteractionProcessAPI<TMapped> {
        interactionBuilder.mapper = Mapper(mapper)
        return InteractionProcessAPI(interactionBuilder)
    }
}