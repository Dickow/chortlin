package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionMapAPI4
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionProcessAPI
import com.dickow.chortlin.core.configuration.interaction.InteractionDefinition
import com.dickow.chortlin.core.configuration.map.Mapper4

class MapAPI4<T1, T2, T3, T4> constructor(private val interactionDefinition: InteractionDefinition)
    : IInteractionMapAPI4<T1, T2, T3, T4> {

    override fun <TMapped> mapTo(mapper: (T1, T2, T3, T4) -> TMapped): IInteractionProcessAPI<TMapped> {
        interactionDefinition.mapper = Mapper4(mapper)
        return InteractionProcessAPI(interactionDefinition)
    }
}