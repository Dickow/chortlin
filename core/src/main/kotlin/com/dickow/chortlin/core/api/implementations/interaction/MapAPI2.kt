package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionMapAPI2
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionProcessAPI
import com.dickow.chortlin.core.configuration.interaction.InteractionBuilder
import com.dickow.chortlin.core.configuration.map.Mapper2

class MapAPI2<T1, T2> constructor(private val interactionBuilder: InteractionBuilder)
    : IInteractionMapAPI2<T1, T2> {

    override fun <TMapped> mapTo(mapper: (T1, T2) -> TMapped): IInteractionProcessAPI<TMapped> {
        interactionBuilder.mapper = Mapper2(mapper)
        return InteractionProcessAPI(interactionBuilder)
    }
}