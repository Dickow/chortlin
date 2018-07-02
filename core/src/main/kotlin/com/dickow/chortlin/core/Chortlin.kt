package com.dickow.chortlin.core

import com.dickow.chortlin.core.api.implementations.EventAPI
import com.dickow.chortlin.core.api.interfaces.IEventAPI
import com.dickow.chortlin.core.event.IEvent

object Chortlin {
    fun <TEvent : IEvent> onEvent(): IEventAPI<TEvent> {
        return EventAPI()
    }
}

