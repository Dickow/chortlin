package com.dickow.chortlin.core.api.implementations

import com.dickow.chortlin.core.api.interfaces.IChannelAPI
import com.dickow.chortlin.core.api.interfaces.IMapAPI1
import com.dickow.chortlin.core.interaction.IChannel
import com.dickow.chortlin.core.message.IMessage

class ChannelAPI<TChannelMsg : IMessage> : IChannelAPI<TChannelMsg> {
    override fun via(channel: IChannel<TChannelMsg>): IMapAPI1<TChannelMsg> {
        return MapAPI<TChannelMsg, Any, Any, Any, Any, Any>()
    }
}