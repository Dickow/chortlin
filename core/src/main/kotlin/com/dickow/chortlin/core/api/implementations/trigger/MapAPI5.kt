package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerMapAPI5
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerProcessAPI
import com.dickow.chortlin.core.configuration.Definition
import com.dickow.chortlin.core.configuration.map.Mapper5

class MapAPI5<T1, T2, T3, T4, T5>
constructor(private val definition: Definition)
    : ITriggerMapAPI5<T1, T2, T3, T4, T5> {

    override fun <TMapped> mapInputTo(mapper: (T1, T2, T3, T4, T5) -> TMapped): ITriggerProcessAPI<TMapped> {
        definition.mapper = Mapper5(mapper)
        return TriggerProcessAPI(definition)
    }
}