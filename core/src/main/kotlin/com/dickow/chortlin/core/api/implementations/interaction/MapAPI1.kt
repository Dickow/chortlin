package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionMapAPI1
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionProcessAPI
import com.dickow.chortlin.core.configuration.interaction.InteractionBuilder
import com.dickow.chortlin.core.configuration.map.Mapper1

class MapAPI1<T1> constructor(private val interactionBuilder: InteractionBuilder)
    : IInteractionMapAPI1<T1> {

    override fun <TMapped> mapTo(mapper: (T1) -> TMapped): IInteractionProcessAPI<TMapped> {
        interactionBuilder.mapper = Mapper1(mapper)
        return InteractionProcessAPI(interactionBuilder)
    }
}