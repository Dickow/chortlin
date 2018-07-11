package com.dickow.chortlin.core.api.implementations

import com.dickow.chortlin.core.api.implementations.interaction.InteractionProcessAPI
import com.dickow.chortlin.core.api.implementations.trigger.TriggerProcessAPI
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionMapAPI4
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionProcessAPI
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerMapAPI4
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerProcessAPI
import com.dickow.chortlin.core.intercept.builders.ReceiveInterceptor4Builder

class MapAPI4<TClass, T1, T2, T3, T4, R> constructor(
        private val endpoint: Any,
        private val method: (TClass, T1, T2, T3, T4) -> R
) : IInteractionMapAPI4<T1, T2, T3, T4>, ITriggerMapAPI4<T1, T2, T3, T4> {

    override fun <TMapped> mapInputTo(mapper: (T1, T2, T3, T4) -> TMapped): ITriggerProcessAPI<TMapped> {
        return TriggerProcessAPI()
    }

    override fun <TMapped> mapTo(mapper: (T1, T2, T3, T4) -> TMapped): IInteractionProcessAPI<TMapped> {
        return InteractionProcessAPI(endpoint, this, ReceiveInterceptor4Builder(method, mapper))
    }
}