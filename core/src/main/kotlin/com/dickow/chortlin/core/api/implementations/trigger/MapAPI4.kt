package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerMapAPI4
import com.dickow.chortlin.core.api.interfaces.trigger.TriggerAPI
import com.dickow.chortlin.core.api.interfaces.trigger.TriggerProcessAPI
import com.dickow.chortlin.core.configuration.Subscriber
import com.dickow.chortlin.core.configuration.map.Mapper4
import com.dickow.chortlin.core.configuration.process.Processor1
import com.dickow.chortlin.core.configuration.trigger.TriggerBuilder
import com.dickow.chortlin.core.handlers.IHandler4

class MapAPI4<T1, T2, T3, T4>(private val definition: TriggerBuilder, private val subscriber: Subscriber)
    : ITriggerMapAPI4<T1, T2, T3, T4> {
    override fun <T1, T2, T3, T4, TMapped, R> handleWith(handler: IHandler4<T1, T2, T3, T4, TMapped, R>): TriggerAPI<R> {
        definition.mapper = Mapper4(handler::mapInput)
        definition.processor = Processor1(handler::process)
        return TriggerAPI(definition, subscriber)
    }

    override fun <TMapped> mapInputTo(mapper: (T1, T2, T3, T4) -> TMapped): TriggerProcessAPI<TMapped> {
        definition.mapper = Mapper4(mapper)
        return TriggerProcessAPI(definition, subscriber)
    }
}