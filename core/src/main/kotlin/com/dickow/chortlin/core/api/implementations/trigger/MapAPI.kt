package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerMapAPI
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerProcessAPI
import com.dickow.chortlin.core.configuration.Definition
import com.dickow.chortlin.core.configuration.map.Mapper

class MapAPI constructor(private val definition: Definition) : ITriggerMapAPI {
    override fun <TMapped> mapInputTo(mapper: () -> TMapped): ITriggerProcessAPI<TMapped> {
        definition.mapper = Mapper(mapper)
        return TriggerProcessAPI(definition)
    }
}