package com.dickow.chortlin.core.api.implementations.interaction

import com.dickow.chortlin.core.api.interfaces.interaction.InteractionAPI
import com.dickow.chortlin.core.api.interfaces.interaction.InteractionMapAPI1
import com.dickow.chortlin.core.api.interfaces.interaction.InteractionProcessAPI
import com.dickow.chortlin.core.configuration.Subscriber
import com.dickow.chortlin.core.configuration.interaction.InteractionBuilder
import com.dickow.chortlin.core.configuration.map.Mapper1
import com.dickow.chortlin.core.configuration.process.Processor1
import com.dickow.chortlin.core.handlers.IHandler1

class MapAPI1<T1> constructor(
        private val interactionBuilder: InteractionBuilder, private val subscriber: Subscriber)
    : InteractionMapAPI1<T1> {

    override fun <T1, TMapped, R> handleWith(handler: IHandler1<T1, TMapped, R>): InteractionAPI<T1, R> {
        interactionBuilder.mapper = Mapper1(handler::mapInput)
        interactionBuilder.processor = Processor1(handler::process)
        return InteractionAPI(interactionBuilder, subscriber)
    }

    override fun <TMapped> mapTo(mapper: (T1) -> TMapped): InteractionProcessAPI<T1, TMapped> {
        interactionBuilder.mapper = Mapper1(mapper)
        return InteractionProcessAPI(interactionBuilder, subscriber)
    }
}