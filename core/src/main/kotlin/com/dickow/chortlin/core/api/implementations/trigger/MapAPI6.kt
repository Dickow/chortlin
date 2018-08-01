package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerMapAPI6
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerProcessAPI
import com.dickow.chortlin.core.configuration.Definition
import com.dickow.chortlin.core.configuration.map.Mapper6

class MapAPI6<T1, T2, T3, T4, T5, T6>
constructor(private val definition: Definition)
    : ITriggerMapAPI6<T1, T2, T3, T4, T5, T6> {

    override fun <TMapped> mapInputTo(mapper: (T1, T2, T3, T4, T5, T6) -> TMapped): ITriggerProcessAPI<TMapped> {
        definition.mapper = Mapper6(mapper)
        return TriggerProcessAPI(definition)
    }
}