package com.dickow.chortlin.core.api.interfaces

import com.dickow.chortlin.core.message.IMessage

interface IMessageAPI<TReceivedMsg : IMessage, TProcessMsg : IMessage> {
    fun <R> thenInteractWith(receiver: (TReceivedMsg) -> R): IChannelAPI<TReceivedMsg, R>
}