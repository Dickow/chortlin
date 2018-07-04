package com.dickow.chortlin.core.api.interfaces

import com.dickow.chortlin.core.message.IMessage

interface IMessageAPI<TReceivedMsg : IMessage> {
    fun <TClass, R> thenInteractWith(receiver: (TClass, TReceivedMsg) -> R): IChannelAPI<TReceivedMsg>
}