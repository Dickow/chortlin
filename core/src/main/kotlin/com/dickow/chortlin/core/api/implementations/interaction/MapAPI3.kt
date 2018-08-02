package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionMapAPI3
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionProcessAPI
import com.dickow.chortlin.core.configuration.interaction.InteractionBuilder
import com.dickow.chortlin.core.configuration.map.Mapper3

class MapAPI3<T1, T2, T3> constructor(private val interactionBuilder: InteractionBuilder)
    : IInteractionMapAPI3<T1, T2, T3> {

    override fun <TMapped> mapTo(mapper: (T1, T2, T3) -> TMapped): IInteractionProcessAPI<TMapped> {
        interactionBuilder.mapper = Mapper3(mapper)
        return InteractionProcessAPI(interactionBuilder)
    }
}