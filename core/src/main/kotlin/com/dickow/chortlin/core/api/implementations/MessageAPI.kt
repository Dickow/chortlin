package com.dickow.chortlin.core.api.implementations

import com.dickow.chortlin.core.api.interfaces.IChannelAPI
import com.dickow.chortlin.core.api.interfaces.IMessageAPI
import com.dickow.chortlin.core.message.IMessage

class MessageAPI<TReceivedMsg : IMessage> : IMessageAPI<TReceivedMsg> {
    override fun <TClass, R> thenInteractWith(receiver: (TClass, TReceivedMsg) -> R): IChannelAPI<TReceivedMsg> {
        return ChannelAPI()
    }
}