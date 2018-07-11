package com.dickow.chortlin.core.api.implementations

import com.dickow.chortlin.core.api.implementations.interaction.InteractionProcessAPI
import com.dickow.chortlin.core.api.implementations.trigger.TriggerProcessAPI
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionMapAPI3
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionProcessAPI
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerMapAPI3
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerProcessAPI
import com.dickow.chortlin.core.intercept.builders.ReceiveInterceptor3Builder

class MapAPI3<TClass, T1, T2, T3, R> constructor(
        private val endpoint: Any,
        private val method: (TClass, T1, T2, T3) -> R
) : IInteractionMapAPI3<T1, T2, T3>, ITriggerMapAPI3<T1, T2, T3> {

    override fun <TMapped> mapInputTo(mapper: (T1, T2, T3) -> TMapped): ITriggerProcessAPI<TMapped> {
        return TriggerProcessAPI()
    }

    override fun <TMapped> mapTo(mapper: (T1, T2, T3) -> TMapped): IInteractionProcessAPI<TMapped> {
        return InteractionProcessAPI(endpoint, this, ReceiveInterceptor3Builder(method, mapper))
    }
}