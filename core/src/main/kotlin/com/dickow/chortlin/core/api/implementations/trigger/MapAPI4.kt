package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerMapAPI4
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerProcessAPI
import com.dickow.chortlin.core.configuration.map.Mapper4
import com.dickow.chortlin.core.configuration.trigger.TriggerBuilder

class MapAPI4<T1, T2, T3, T4> constructor(private val definition: TriggerBuilder)
    : ITriggerMapAPI4<T1, T2, T3, T4> {

    override fun <TMapped> mapInputTo(mapper: (T1, T2, T3, T4) -> TMapped): ITriggerProcessAPI<TMapped> {
        definition.mapper = Mapper4(mapper)
        return TriggerProcessAPI(definition)
    }
}