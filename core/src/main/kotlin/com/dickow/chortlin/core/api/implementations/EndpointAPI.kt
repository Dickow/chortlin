package com.dickow.chortlin.core.api.implementations

import com.dickow.chortlin.core.api.interfaces.*

class EndpointAPI : IEndpointAPI {
    override fun <TClass, R> onTrigger(method: (TClass) -> R): IMapAPI {
        return MapAPI<Any, Any, Any, Any, Any, Any>()
    }

    override fun <TClass, T1, R> onTrigger(method: (TClass, T1) -> R): IMapAPI1<T1> {
        return MapAPI<T1, Any, Any, Any, Any, Any>()
    }

    override fun <TClass, T1, T2, R> onTrigger(method: (TClass, T1, T2) -> R): IMapAPI2<T1, T2> {
        return MapAPI<T1, T2, Any, Any, Any, Any>()
    }

    override fun <TClass, T1, T2, T3, R> onTrigger(method: (TClass, T1, T2, T3) -> R): IMapAPI3<T1, T2, T3> {
        return MapAPI<T1, T2, T3, Any, Any, Any>()
    }

    override fun <TClass, T1, T2, T3, T4, R> onTrigger(method: (TClass, T1, T2, T3, T4) -> R): IMapAPI4<T1, T2, T3, T4> {
        return MapAPI<T1, T2, T3, T4, Any, Any>()
    }

    override fun <TClass, T1, T2, T3, T4, T5, R> onTrigger(method: (TClass, T1, T2, T3, T4, T5) -> R): IMapAPI5<T1, T2, T3, T4, T5> {
        return MapAPI<T1, T2, T3, T4, T5, Any>()
    }

    override fun <TClass, T1, T2, T3, T4, T5, T6, R> onTrigger(method: (TClass, T1, T2, T3, T4, T5, T6) -> R): IMapAPI6<T1, T2, T3, T4, T5, T6> {
        return MapAPI<T1, T2, T3, T4, T5, T6>()
    }

}