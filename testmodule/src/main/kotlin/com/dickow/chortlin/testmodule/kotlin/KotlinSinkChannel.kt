package com.dickow.chortlin.testmodule.kotlin

import com.dickow.chortlin.core.message.Channel
import com.dickow.chortlin.core.message.IMessage

class KotlinSinkChannel<T> : Channel<T> {
    override fun send(message: IMessage<T>) {
        println("Om nom nom ${message.getPayload()}")
    }
}