package com.dickow.chortlin.core.api.interfaces

import com.dickow.chortlin.core.configuration.IChannel

interface IChannelAPI<TChannelMsg> {
    fun via(channel: IChannel<TChannelMsg>)
}