package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionAPI
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionMapAPI3
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionProcessAPI
import com.dickow.chortlin.core.configuration.interaction.InteractionBuilder
import com.dickow.chortlin.core.configuration.map.Mapper3
import com.dickow.chortlin.core.configuration.process.Processor1
import com.dickow.chortlin.core.handlers.IHandler3

class MapAPI3<T1, T2, T3> constructor(private val interactionBuilder: InteractionBuilder)
    : IInteractionMapAPI3<T1, T2, T3> {
    override fun <T1, T2, T3, TMapped, R> handleWith(handler: IHandler3<T1, T2, T3, TMapped, R>): IInteractionAPI {
        interactionBuilder.mapper = Mapper3(handler::mapInput)
        interactionBuilder.processor = Processor1(handler::process)
        return InteractionAPI(interactionBuilder)
    }

    override fun <TMapped> mapTo(mapper: (T1, T2, T3) -> TMapped): IInteractionProcessAPI<TMapped> {
        interactionBuilder.mapper = Mapper3(mapper)
        return InteractionProcessAPI(interactionBuilder)
    }
}