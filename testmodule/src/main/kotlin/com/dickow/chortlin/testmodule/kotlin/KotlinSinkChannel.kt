package com.dickow.chortlin.testmodule.kotlin

import com.dickow.chortlin.core.message.Channel
import com.dickow.chortlin.core.message.Message

class KotlinSinkChannel<T> : Channel<T> {
    override fun send(message: Message<T>) {
        println("Om nom nom ${message.payload}")
    }
}