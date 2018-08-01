package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerMapAPI1
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerProcessAPI
import com.dickow.chortlin.core.configuration.Definition
import com.dickow.chortlin.core.configuration.map.Mapper1

class MapAPI1<T1> constructor(private val definition: Definition)
    : ITriggerMapAPI1<T1> {

    override fun <TMapped> mapInputTo(mapper: (T1) -> TMapped): ITriggerProcessAPI<TMapped> {
        definition.mapper = Mapper1(mapper)
        return TriggerProcessAPI(definition)
    }
}