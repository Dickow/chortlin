package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerAPI
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerMapAPI5
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerProcessAPI
import com.dickow.chortlin.core.configuration.map.Mapper5
import com.dickow.chortlin.core.configuration.process.Processor1
import com.dickow.chortlin.core.configuration.trigger.TriggerBuilder
import com.dickow.chortlin.core.handlers.IHandler5

class MapAPI5<T1, T2, T3, T4, T5>
constructor(private val definition: TriggerBuilder)
    : ITriggerMapAPI5<T1, T2, T3, T4, T5> {
    override fun <T1, T2, T3, T4, T5, TMapped, R> handleWith(handler: IHandler5<T1, T2, T3, T4, T5, TMapped, R>): ITriggerAPI {
        definition.mapper = Mapper5(handler::mapInput)
        definition.processor = Processor1(handler::process)
        return TriggerAPI(definition)
    }

    override fun <TMapped> mapInputTo(mapper: (T1, T2, T3, T4, T5) -> TMapped): ITriggerProcessAPI<TMapped> {
        definition.mapper = Mapper5(mapper)
        return TriggerProcessAPI(definition)
    }
}