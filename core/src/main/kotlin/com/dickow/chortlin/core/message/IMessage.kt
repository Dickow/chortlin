package com.dickow.chortlin.core.message

interface IMessage<T> {
    fun getPayload(): T
}