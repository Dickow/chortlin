package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerMapAPI
import com.dickow.chortlin.core.api.interfaces.trigger.TriggerAPI
import com.dickow.chortlin.core.api.interfaces.trigger.TriggerProcessAPI
import com.dickow.chortlin.core.configuration.Subscriber
import com.dickow.chortlin.core.configuration.map.Mapper
import com.dickow.chortlin.core.configuration.process.Processor1
import com.dickow.chortlin.core.configuration.trigger.TriggerBuilder
import com.dickow.chortlin.core.handlers.IHandler

class MapAPI(private val definition: TriggerBuilder, private val subscriber: Subscriber) : ITriggerMapAPI {

    override fun <TMapped, R> handleWith(handler: IHandler<TMapped, R>): TriggerAPI<R> {
        definition.mapper = Mapper(handler::mapInput)
        definition.processor = Processor1(handler::process)
        return TriggerAPI(definition, subscriber)
    }

    override fun <TMapped> mapInputTo(mapper: () -> TMapped): TriggerProcessAPI<TMapped> {
        definition.mapper = Mapper(mapper)
        return TriggerProcessAPI(definition, subscriber)
    }
}