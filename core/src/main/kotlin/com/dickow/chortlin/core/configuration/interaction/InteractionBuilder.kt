package com.dickow.chortlin.core.configuration.interaction

import com.dickow.chortlin.core.configuration.Definition
import com.dickow.chortlin.core.configuration.IChannel

class InteractionBuilder : Definition() {
    override fun noChannel(): Interaction {
        return Interaction(endpoint, mapper, processor, interactions)
    }

    override fun <TMsg> configureChannel(channel: IChannel<TMsg>): Interaction {
        return Interaction(endpoint, mapper, processor, interactions)
    }
}