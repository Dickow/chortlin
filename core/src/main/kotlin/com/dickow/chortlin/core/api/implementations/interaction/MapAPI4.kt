package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionAPI
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionMapAPI4
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionProcessAPI
import com.dickow.chortlin.core.configuration.interaction.InteractionBuilder
import com.dickow.chortlin.core.configuration.map.Mapper4
import com.dickow.chortlin.core.configuration.process.Processor1
import com.dickow.chortlin.core.handlers.IHandler4

class MapAPI4<T1, T2, T3, T4> constructor(private val interactionBuilder: InteractionBuilder)
    : IInteractionMapAPI4<T1, T2, T3, T4> {

    override fun <T1, T2, T3, T4, TMapped, R> handleWith(handler: IHandler4<T1, T2, T3, T4, TMapped, R>): IInteractionAPI {
        interactionBuilder.mapper = Mapper4(handler::mapInput)
        interactionBuilder.processor = Processor1(handler::process)
        return InteractionAPI(interactionBuilder)
    }

    override fun <TMapped> mapTo(mapper: (T1, T2, T3, T4) -> TMapped): IInteractionProcessAPI<TMapped> {
        interactionBuilder.mapper = Mapper4(mapper)
        return InteractionProcessAPI(interactionBuilder)
    }
}