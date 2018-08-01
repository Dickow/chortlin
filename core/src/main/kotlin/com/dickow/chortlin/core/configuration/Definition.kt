package com.dickow.chortlin.core.configuration

import com.dickow.chortlin.core.configuration.interaction.Interaction
import com.dickow.chortlin.core.configuration.map.IMapper

abstract class Definition {
    internal lateinit var endpoint: Any
    internal lateinit var mapper: IMapper
    internal lateinit var processor: Any
    internal var interactions: Collection<Interaction> = emptyList()

    abstract fun <TMsg> configureChannel(channel: IChannel<TMsg>): ChortlinConfiguration
}