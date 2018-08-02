package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionAPI
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionMapAPI5
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionProcessAPI
import com.dickow.chortlin.core.configuration.interaction.InteractionBuilder
import com.dickow.chortlin.core.configuration.map.Mapper5
import com.dickow.chortlin.core.configuration.process.Processor1
import com.dickow.chortlin.core.handlers.IHandler5

class MapAPI5<T1, T2, T3, T4, T5>
constructor(private val interactionBuilder: InteractionBuilder)
    : IInteractionMapAPI5<T1, T2, T3, T4, T5> {

    override fun <T1, T2, T3, T4, T5, TMapped, R> handleWith(handler: IHandler5<T1, T2, T3, T4, T5, TMapped, R>): IInteractionAPI {
        interactionBuilder.mapper = Mapper5(handler::mapInput)
        interactionBuilder.processor = Processor1(handler::process)
        return InteractionAPI(interactionBuilder)
    }

    override fun <TMapped> mapTo(mapper: (T1, T2, T3, T4, T5) -> TMapped): IInteractionProcessAPI<TMapped> {
        interactionBuilder.mapper = Mapper5(mapper)
        return InteractionProcessAPI(interactionBuilder)
    }
}