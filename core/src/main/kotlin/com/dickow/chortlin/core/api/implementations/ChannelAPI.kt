package com.dickow.chortlin.core.api.implementations

import com.dickow.chortlin.core.api.interfaces.IChannelAPI
import com.dickow.chortlin.core.configuration.IChannel

class ChannelAPI<TChannelMsg> : IChannelAPI<TChannelMsg> {
    override fun via(channel: IChannel<TChannelMsg>) {}
}