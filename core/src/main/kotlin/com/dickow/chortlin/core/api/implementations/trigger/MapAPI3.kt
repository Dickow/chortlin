package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerMapAPI3
import com.dickow.chortlin.core.api.interfaces.trigger.TriggerAPI
import com.dickow.chortlin.core.api.interfaces.trigger.TriggerProcessAPI
import com.dickow.chortlin.core.configuration.Subscriber
import com.dickow.chortlin.core.configuration.map.Mapper3
import com.dickow.chortlin.core.configuration.process.Processor1
import com.dickow.chortlin.core.configuration.trigger.TriggerBuilder
import com.dickow.chortlin.core.handlers.IHandler3

class MapAPI3<T1, T2, T3>(private val definition: TriggerBuilder, private val subscriber: Subscriber)
    : ITriggerMapAPI3<T1, T2, T3> {
    override fun <T1, T2, T3, TMapped, R> handleWith(handler: IHandler3<T1, T2, T3, TMapped, R>): TriggerAPI<R> {
        definition.mapper = Mapper3(handler::mapInput)
        definition.processor = Processor1(handler::process)
        return TriggerAPI(definition, subscriber)
    }

    override fun <TMapped> mapInputTo(mapper: (T1, T2, T3) -> TMapped): TriggerProcessAPI<TMapped> {
        definition.mapper = Mapper3(mapper)
        return TriggerProcessAPI(definition, subscriber)
    }
}