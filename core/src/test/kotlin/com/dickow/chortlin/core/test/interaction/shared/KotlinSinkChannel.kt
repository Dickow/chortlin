package com.dickow.chortlin.core.test.interaction.shared

import com.dickow.chortlin.core.message.Channel
import com.dickow.chortlin.core.message.Message

class KotlinSinkChannel<TMsg> : Channel<TMsg> {
    override fun send(message: Message<TMsg>) {
        println("Dumped in the sink ${message.payload}")
    }
}