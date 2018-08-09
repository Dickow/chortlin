package com.dickow.chortlin.core.message

import java.time.LocalDateTime

class Message<T> constructor(private val payload: T) : IMessage<T> {
    internal val timeStamp = LocalDateTime.now()

    override fun getPayload(): T {
        return payload
    }
}