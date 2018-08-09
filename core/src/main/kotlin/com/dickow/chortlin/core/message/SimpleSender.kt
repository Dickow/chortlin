package com.dickow.chortlin.core.message

class SimpleSender<TMsg> constructor(private val channel: Channel<TMsg>) : Sender {
    @Suppress("UNCHECKED_CAST")
    override fun send(msg: Any?) {
        val wrapped = Message(msg as TMsg)
        channel.send(wrapped)
    }
}