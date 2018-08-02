package com.dickow.chortlin.core.configuration.trigger

import com.dickow.chortlin.core.configuration.Definition
import com.dickow.chortlin.core.configuration.IChannel

class TriggerBuilder : Definition() {

    override fun noChannel(): Trigger {
        return Trigger(endpoint, mapper, processor, interactions)
    }

    override fun <TMsg> configureChannel(channel: IChannel<TMsg>): Trigger {
        return Trigger(endpoint, mapper, processor, interactions)
    }
}