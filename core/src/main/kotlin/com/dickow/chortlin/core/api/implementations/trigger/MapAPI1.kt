package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerAPI
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerMapAPI1
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerProcessAPI
import com.dickow.chortlin.core.configuration.Definition
import com.dickow.chortlin.core.configuration.map.Mapper1
import com.dickow.chortlin.core.configuration.process.Processor1
import com.dickow.chortlin.core.handlers.IHandler1

class MapAPI1<T1> constructor(private val definition: Definition)
    : ITriggerMapAPI1<T1> {
    override fun <T1, TMapped, R> handleWith(handler: IHandler1<T1, TMapped, R>): ITriggerAPI {
        definition.mapper = Mapper1(handler::mapInput)
        definition.processor = Processor1(handler::process)
        return TriggerAPI(definition)
    }

    override fun <TMapped> mapInputTo(mapper: (T1) -> TMapped): ITriggerProcessAPI<TMapped> {
        definition.mapper = Mapper1(mapper)
        return TriggerProcessAPI(definition)
    }
}