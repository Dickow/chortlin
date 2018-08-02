package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionAPI
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionMapAPI6
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionProcessAPI
import com.dickow.chortlin.core.configuration.interaction.InteractionBuilder
import com.dickow.chortlin.core.configuration.map.Mapper6
import com.dickow.chortlin.core.configuration.process.Processor1
import com.dickow.chortlin.core.handlers.IHandler6

class MapAPI6<T1, T2, T3, T4, T5, T6>
constructor(private val interactionBuilder: InteractionBuilder)
    : IInteractionMapAPI6<T1, T2, T3, T4, T5, T6> {

    override fun <T1, T2, T3, T4, T5, T6, TMapped, R> handleWith(handler: IHandler6<T1, T2, T3, T4, T5, T6, TMapped, R>): IInteractionAPI {
        interactionBuilder.mapper = Mapper6(handler::mapInput)
        interactionBuilder.processor = Processor1(handler::process)
        return InteractionAPI(interactionBuilder)
    }

    override fun <TMapped> mapTo(mapper: (T1, T2, T3, T4, T5, T6) -> TMapped): IInteractionProcessAPI<TMapped> {
        interactionBuilder.mapper = Mapper6(mapper)
        return InteractionProcessAPI(interactionBuilder)
    }
}