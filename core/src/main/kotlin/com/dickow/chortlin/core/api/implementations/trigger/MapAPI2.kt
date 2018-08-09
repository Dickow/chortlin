package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerMapAPI2
import com.dickow.chortlin.core.api.interfaces.trigger.TriggerAPI
import com.dickow.chortlin.core.api.interfaces.trigger.TriggerProcessAPI
import com.dickow.chortlin.core.configuration.Subscriber
import com.dickow.chortlin.core.configuration.map.Mapper2
import com.dickow.chortlin.core.configuration.process.Processor1
import com.dickow.chortlin.core.configuration.trigger.TriggerBuilder
import com.dickow.chortlin.core.handlers.IHandler2

class MapAPI2<T1, T2>(private val definition: TriggerBuilder, private val subscriber: Subscriber) : ITriggerMapAPI2<T1, T2> {

    override fun <T1, T2, TMapped, R> handleWith(handler: IHandler2<T1, T2, TMapped, R>): TriggerAPI<R> {
        definition.mapper = Mapper2(handler::mapInput)
        definition.processor = Processor1(handler::process)
        return TriggerAPI(definition, subscriber)
    }

    override fun <TMapped> mapInputTo(mapper: (T1, T2) -> TMapped): TriggerProcessAPI<TMapped> {
        definition.mapper = Mapper2(mapper)
        return TriggerProcessAPI(definition, subscriber)
    }
}