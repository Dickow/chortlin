package com.dickow.chortlin.core.message

class Message<T> constructor(private val payload: T) : IMessage<T> {
    override fun getPayload(): T {
        return payload
    }
}