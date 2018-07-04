package com.dickow.chortlin.core.api.interfaces

import com.dickow.chortlin.core.interaction.IChannel
import com.dickow.chortlin.core.message.IMessage

interface IChannelAPI<TChannelMsg : IMessage> {
    fun via(channel: IChannel<TChannelMsg>): IMapAPI1<TChannelMsg>
}