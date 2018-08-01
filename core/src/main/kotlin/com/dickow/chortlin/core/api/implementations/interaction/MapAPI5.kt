package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionMapAPI5
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionProcessAPI
import com.dickow.chortlin.core.configuration.interaction.InteractionDefinition
import com.dickow.chortlin.core.configuration.map.Mapper5

class MapAPI5<T1, T2, T3, T4, T5>
constructor(private val interactionDefinition: InteractionDefinition)
    : IInteractionMapAPI5<T1, T2, T3, T4, T5> {

    override fun <TMapped> mapTo(mapper: (T1, T2, T3, T4, T5) -> TMapped): IInteractionProcessAPI<TMapped> {
        interactionDefinition.mapper = Mapper5(mapper)
        return InteractionProcessAPI(interactionDefinition)
    }
}