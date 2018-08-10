package com.dickow.chortlin.core.message

import com.dickow.chortlin.core.continuation.Accumulator

class SimpleSender<TMsg> constructor(private val channel: Channel<TMsg>) : Sender {
    @Suppress("UNCHECKED_CAST")
    override fun send(msg: Any?, accumulator: Accumulator) {
        val wrapped = Message<TMsg>()
        wrapped.hashes = accumulator.hashes.plus(accumulator.endpoint.hashCode())
        wrapped.payload = msg as TMsg
        
        channel.send(wrapped)
    }
}