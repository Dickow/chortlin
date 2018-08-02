package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionAPI
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionMapAPI
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionProcessAPI
import com.dickow.chortlin.core.configuration.interaction.InteractionBuilder
import com.dickow.chortlin.core.configuration.map.Mapper
import com.dickow.chortlin.core.configuration.process.Processor1
import com.dickow.chortlin.core.handlers.IHandler

class MapAPI constructor(private val interactionBuilder: InteractionBuilder) : IInteractionMapAPI {
    override fun <TMapped, R> handleWith(handler: IHandler<TMapped, R>): IInteractionAPI {
        interactionBuilder.mapper = Mapper(handler::mapInput)
        interactionBuilder.processor = Processor1(handler::process)
        return InteractionAPI(interactionBuilder)
    }

    override fun <TMapped> mapTo(mapper: () -> TMapped): IInteractionProcessAPI<TMapped> {
        interactionBuilder.mapper = Mapper(mapper)
        return InteractionProcessAPI(interactionBuilder)
    }
}