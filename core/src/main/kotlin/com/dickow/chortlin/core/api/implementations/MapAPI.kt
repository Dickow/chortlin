package com.dickow.chortlin.core.api.implementations

import com.dickow.chortlin.core.api.implementations.interaction.InteractionProcessAPI
import com.dickow.chortlin.core.api.implementations.trigger.TriggerProcessAPI
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionMapAPI
import com.dickow.chortlin.core.api.interfaces.interaction.IInteractionProcessAPI
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerMapAPI
import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerProcessAPI
import com.dickow.chortlin.core.intercept.builders.ReceiveInterceptorBuilder

class MapAPI<TClass, R> constructor(
        private val endpoint: Any,
        private val method: (TClass) -> R
) : IInteractionMapAPI, ITriggerMapAPI {
    override fun <TMapped> mapInputTo(mapper: () -> TMapped): ITriggerProcessAPI<TMapped> {
        return TriggerProcessAPI()
    }

    override fun <TMapped> mapTo(mapper: () -> TMapped): IInteractionProcessAPI<TMapped> {
        return InteractionProcessAPI(endpoint, this, ReceiveInterceptorBuilder(method, mapper))
    }
}