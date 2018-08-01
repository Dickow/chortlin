package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionMapAPI6
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionProcessAPI
import com.dickow.chortlin.core.configuration.interaction.InteractionDefinition
import com.dickow.chortlin.core.configuration.map.Mapper6

class MapAPI6<T1, T2, T3, T4, T5, T6>
constructor(private val interactionDefinition: InteractionDefinition)
    : IInteractionMapAPI6<T1, T2, T3, T4, T5, T6> {

    override fun <TMapped> mapTo(mapper: (T1, T2, T3, T4, T5, T6) -> TMapped): IInteractionProcessAPI<TMapped> {
        interactionDefinition.mapper = Mapper6(mapper)
        return InteractionProcessAPI(interactionDefinition)
    }
}