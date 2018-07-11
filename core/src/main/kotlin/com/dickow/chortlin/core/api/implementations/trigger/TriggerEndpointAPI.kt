package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.implementations.*
import com.dickow.chortlin.core.api.interfaces.trigger.*

class TriggerEndpointAPI : ITriggerEndpointAPI {
    override fun <TClass, R> onTrigger(method: (TClass) -> R): ITriggerMapAPI {
        return MapAPI(this, method)
    }

    override fun <TClass, T1, R> onTrigger(method: (TClass, T1) -> R): ITriggerMapAPI1<T1> {
        return MapAPI1(this, method)
    }

    override fun <TClass, T1, T2, R> onTrigger(method: (TClass, T1, T2) -> R): ITriggerMapAPI2<T1, T2> {
        return MapAPI2(this, method)
    }

    override fun <TClass, T1, T2, T3, R> onTrigger(method: (TClass, T1, T2, T3) -> R): ITriggerMapAPI3<T1, T2, T3> {
        return MapAPI3(this, method)
    }

    override fun <TClass, T1, T2, T3, T4, R> onTrigger(method: (TClass, T1, T2, T3, T4) -> R): ITriggerMapAPI4<T1, T2, T3, T4> {
        return MapAPI4(this, method)
    }

    override fun <TClass, T1, T2, T3, T4, T5, R> onTrigger(method: (TClass, T1, T2, T3, T4, T5) -> R): ITriggerMapAPI5<T1, T2, T3, T4, T5> {
        return MapAPI5(this, method)
    }

    override fun <TClass, T1, T2, T3, T4, T5, T6, R> onTrigger(method: (TClass, T1, T2, T3, T4, T5, T6) -> R): ITriggerMapAPI6<T1, T2, T3, T4, T5, T6> {
        return MapAPI6(this, method)
    }

}