package com.dickow.chortlin.core.api.implementations

import com.dickow.chortlin.core.api.implementations.interaction.InteractionProcessAPI
import com.dickow.chortlin.core.api.implementations.trigger.TriggerProcessAPI
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionMapAPI5
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionProcessAPI
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerMapAPI5
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerProcessAPI
import com.dickow.chortlin.core.intercept.builders.ReceiveInterceptor5Builder

class MapAPI5<TClass, T1, T2, T3, T4, T5, R> constructor(
        private val endpoint: Any,
        private val method: (TClass, T1, T2, T3, T4, T5) -> R)
    : IInteractionMapAPI5<T1, T2, T3, T4, T5>, ITriggerMapAPI5<T1, T2, T3, T4, T5> {

    override fun <TMapped> mapInputTo(mapper: (T1, T2, T3, T4, T5) -> TMapped): ITriggerProcessAPI<TMapped> {
        return TriggerProcessAPI()
    }

    override fun <TMapped> mapTo(mapper: (T1, T2, T3, T4, T5) -> TMapped): IInteractionProcessAPI<TMapped> {
        return InteractionProcessAPI(endpoint, this, ReceiveInterceptor5Builder(method, mapper))
    }
}