package com.dickow.chortlin.core.interaction

import com.dickow.chortlin.core.message.IMessage

class InteractionDefinition constructor(
        private val endpoint: Any,
        private val mapper: Any,
        private val processor: Any,
        private val interactions: List<Interaction>
) {
    fun <TMsg : IMessage> configureChannel(channel: IChannel<TMsg>): Interaction {
        return Interaction(endpoint, mapper, processor, interactions, channel)
    }
}