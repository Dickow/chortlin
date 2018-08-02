package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerAPI
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerMapAPI
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerProcessAPI
import com.dickow.chortlin.core.configuration.Definition
import com.dickow.chortlin.core.configuration.map.Mapper
import com.dickow.chortlin.core.configuration.process.Processor1
import com.dickow.chortlin.core.handlers.IHandler

class MapAPI constructor(private val definition: Definition) : ITriggerMapAPI {

    override fun <TMapped, R> handleWith(handler: IHandler<TMapped, R>): ITriggerAPI {
        definition.mapper = Mapper(handler::mapInput)
        definition.processor = Processor1(handler::process)
        return TriggerAPI(definition)
    }

    override fun <TMapped> mapInputTo(mapper: () -> TMapped): ITriggerProcessAPI<TMapped> {
        definition.mapper = Mapper(mapper)
        return TriggerProcessAPI(definition)
    }
}