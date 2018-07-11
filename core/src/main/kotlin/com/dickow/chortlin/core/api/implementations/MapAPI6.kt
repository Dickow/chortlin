package com.dickow.chortlin.core.api.implementations

import com.dickow.chortlin.core.api.implementations.interaction.InteractionProcessAPI
import com.dickow.chortlin.core.api.implementations.trigger.TriggerProcessAPI
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionMapAPI6
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionProcessAPI
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerMapAPI6
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerProcessAPI
import com.dickow.chortlin.core.intercept.builders.ReceiveInterceptor6Builder

class MapAPI6<TClass, T1, T2, T3, T4, T5, T6, R> constructor(
        private val endpoint: Any,
        private val method: (TClass, T1, T2, T3, T4, T5, T6) -> R)
    : IInteractionMapAPI6<T1, T2, T3, T4, T5, T6>, ITriggerMapAPI6<T1, T2, T3, T4, T5, T6> {
    override fun <TMapped> mapInputTo(mapper: (T1, T2, T3, T4, T5, T6) -> TMapped): ITriggerProcessAPI<TMapped> {
        return TriggerProcessAPI()
    }

    override fun <TMapped> mapTo(mapper: (T1, T2, T3, T4, T5, T6) -> TMapped): IInteractionProcessAPI<TMapped> {
        return InteractionProcessAPI(endpoint, this, ReceiveInterceptor6Builder(method, mapper))
    }
}