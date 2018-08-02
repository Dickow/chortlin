package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.*
import com.dickow.chortlin.core.configuration.trigger.TriggerBuilder

class TriggerEndpointAPI : ITriggerEndpointAPI {
    override fun <TClass, R> onTrigger(method: (TClass) -> R): ITriggerMapAPI {
        val definition = TriggerBuilder()
        definition.endpoint = method
        return MapAPI(definition)
    }

    override fun <TClass, T1, R> onTrigger(method: (TClass, T1) -> R): ITriggerMapAPI1<T1> {
        val definition = TriggerBuilder()
        definition.endpoint = method
        return MapAPI1(definition)
    }

    override fun <TClass, T1, T2, R> onTrigger(method: (TClass, T1, T2) -> R): ITriggerMapAPI2<T1, T2> {
        val definition = TriggerBuilder()
        definition.endpoint = method
        return MapAPI2(definition)
    }

    override fun <TClass, T1, T2, T3, R> onTrigger(method: (TClass, T1, T2, T3) -> R): ITriggerMapAPI3<T1, T2, T3> {
        val definition = TriggerBuilder()
        definition.endpoint = method
        return MapAPI3(definition)
    }

    override fun <TClass, T1, T2, T3, T4, R> onTrigger(method: (TClass, T1, T2, T3, T4) -> R): ITriggerMapAPI4<T1, T2, T3, T4> {
        val definition = TriggerBuilder()
        definition.endpoint = method
        return MapAPI4(definition)
    }

    override fun <TClass, T1, T2, T3, T4, T5, R> onTrigger(method: (TClass, T1, T2, T3, T4, T5) -> R): ITriggerMapAPI5<T1, T2, T3, T4, T5> {
        val definition = TriggerBuilder()
        definition.endpoint = method
        return MapAPI5(definition)
    }

    override fun <TClass, T1, T2, T3, T4, T5, T6, R> onTrigger(method: (TClass, T1, T2, T3, T4, T5, T6) -> R): ITriggerMapAPI6<T1, T2, T3, T4, T5, T6> {
        val definition = TriggerBuilder()
        definition.endpoint = method
        return MapAPI6(definition)
    }

}