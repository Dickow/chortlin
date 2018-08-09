package com.dickow.chortlin.core.api.interfaces

import com.dickow.chortlin.core.message.Channel

interface IChannelAPI<TChannelMsg> {
    fun via(channel: Channel<TChannelMsg>)
}