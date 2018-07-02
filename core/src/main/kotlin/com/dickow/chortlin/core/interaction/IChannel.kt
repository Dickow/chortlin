package com.dickow.chortlin.core.interaction

import com.dickow.chortlin.core.message.IMessage

interface IChannel<TMsg : IMessage> {
    fun send(message: TMsg)
}