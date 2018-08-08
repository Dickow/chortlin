package com.dickow.chortlin.core.configuration.trigger

import com.dickow.chortlin.core.Chortlin
import com.dickow.chortlin.core.configuration.Definition
import com.dickow.chortlin.core.configuration.IChannel

class TriggerBuilder : Definition() {

    override fun noChannel(): Trigger {
        val trigger = Trigger(endpoint, mapper, processor, interactions)
        Chortlin.addConfiguration(trigger)
        return trigger
    }

    override fun <TMsg> configureChannel(channel: IChannel<TMsg>): Trigger {
        val trigger = Trigger(endpoint, mapper, processor, interactions)
        Chortlin.addConfiguration(trigger)
        return trigger
    }
}