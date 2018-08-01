package com.dickow.chortlin.scenariotests.shared

import com.dickow.chortlin.core.configuration.IChannel
import com.dickow.chortlin.core.message.IMessage

class SinkChannel : IChannel<String> {
    override fun send(message: IMessage<String>) {
        println("Om nom nom ${message.getPayload()}")
    }

}