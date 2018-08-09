package com.dickow.chortlin.core.message

interface Channel<TMsg> {
    fun send(message: IMessage<TMsg>)
}