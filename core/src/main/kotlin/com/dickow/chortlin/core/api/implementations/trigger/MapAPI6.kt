package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerMapAPI6
import com.dickow.chortlin.core.api.interfaces.trigger.TriggerAPI
import com.dickow.chortlin.core.api.interfaces.trigger.TriggerProcessAPI
import com.dickow.chortlin.core.configuration.map.Mapper6
import com.dickow.chortlin.core.configuration.process.Processor1
import com.dickow.chortlin.core.configuration.trigger.TriggerBuilder
import com.dickow.chortlin.core.handlers.IHandler6

class MapAPI6<T1, T2, T3, T4, T5, T6>
constructor(private val definition: TriggerBuilder)
    : ITriggerMapAPI6<T1, T2, T3, T4, T5, T6> {
    override fun <T1, T2, T3, T4, T5, T6, TMapped, R> handleWith(handler: IHandler6<T1, T2, T3, T4, T5, T6, TMapped, R>): TriggerAPI<R> {
        definition.mapper = Mapper6(handler::mapInput)
        definition.processor = Processor1(handler::process)
        return TriggerAPI(definition)
    }

    override fun <TMapped> mapInputTo(mapper: (T1, T2, T3, T4, T5, T6) -> TMapped): TriggerProcessAPI<TMapped> {
        definition.mapper = Mapper6(mapper)
        return TriggerProcessAPI(definition)
    }
}