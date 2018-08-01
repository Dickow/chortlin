package com.dickow.chortlin.core.configuration.trigger

import com.dickow.chortlin.core.configuration.Definition
import com.dickow.chortlin.core.configuration.IChannel

class TriggerDefinition : Definition() {
    override fun <TMsg> configureChannel(channel: IChannel<TMsg>): Trigger {
        return Trigger(endpoint, mapper, processor, interactions, channel)
    }
}