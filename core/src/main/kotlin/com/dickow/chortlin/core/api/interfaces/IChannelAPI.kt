package com.dickow.chortlin.core.api.interfaces

import com.dickow.chortlin.core.interaction.IChannel
import com.dickow.chortlin.core.message.IMessage

interface IChannelAPI<TChannelMsg : IMessage, TEndpointR, TProcessMsg : IMessage> {
    fun via(channel: IChannel<TChannelMsg>): IMapAPI<(TChannelMsg) -> TProcessMsg, TProcessMsg>
}