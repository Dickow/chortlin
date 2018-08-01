package com.dickow.chortlin.core.configuration

import com.dickow.chortlin.core.message.IMessage

interface IChannel<TMsg> {
    fun send(message: IMessage<TMsg>)
}