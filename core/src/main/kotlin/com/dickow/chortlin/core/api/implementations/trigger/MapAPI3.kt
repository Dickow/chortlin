package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerMapAPI3
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerProcessAPI
import com.dickow.chortlin.core.configuration.Definition
import com.dickow.chortlin.core.configuration.map.Mapper3

class MapAPI3<T1, T2, T3> constructor(private val definition: Definition)
    : ITriggerMapAPI3<T1, T2, T3> {

    override fun <TMapped> mapInputTo(mapper: (T1, T2, T3) -> TMapped): ITriggerProcessAPI<TMapped> {
        definition.mapper = Mapper3(mapper)
        return TriggerProcessAPI(definition)
    }
}