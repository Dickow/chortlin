package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerMapAPI2
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerProcessAPI
import com.dickow.chortlin.core.configuration.Definition
import com.dickow.chortlin.core.configuration.map.Mapper2

class MapAPI2<T1, T2> constructor(private val definition: Definition)
    : ITriggerMapAPI2<T1, T2> {

    override fun <TMapped> mapInputTo(mapper: (T1, T2) -> TMapped): ITriggerProcessAPI<TMapped> {
        definition.mapper = Mapper2(mapper)
        return TriggerProcessAPI(definition)
    }
}