package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerMapAPI1
import com.dickow.chortlin.core.api.interfaces.trigger.TriggerAPI
import com.dickow.chortlin.core.api.interfaces.trigger.TriggerProcessAPI
import com.dickow.chortlin.core.configuration.Subscriber
import com.dickow.chortlin.core.configuration.map.Mapper1
import com.dickow.chortlin.core.configuration.process.Processor1
import com.dickow.chortlin.core.configuration.trigger.TriggerBuilder
import com.dickow.chortlin.core.handlers.IHandler1

class MapAPI1<T1>(private val definition: TriggerBuilder, private val subscriber: Subscriber)
    : ITriggerMapAPI1<T1> {
    override fun <T1, TMapped, R> handleWith(handler: IHandler1<T1, TMapped, R>): TriggerAPI<R> {
        definition.mapper = Mapper1(handler::mapInput)
        definition.processor = Processor1(handler::process)
        return TriggerAPI(definition, subscriber)
    }

    override fun <TMapped> mapInputTo(mapper: (T1) -> TMapped): TriggerProcessAPI<TMapped> {
        definition.mapper = Mapper1(mapper)
        return TriggerProcessAPI(definition, subscriber)
    }
}