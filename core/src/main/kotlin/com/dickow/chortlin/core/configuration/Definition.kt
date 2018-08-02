package com.dickow.chortlin.core.configuration

import com.dickow.chortlin.core.configuration.interaction.Interaction
import com.dickow.chortlin.core.configuration.map.IMapper
import com.dickow.chortlin.core.configuration.process.IProcessor

abstract class Definition {
    internal lateinit var endpoint: Any
    internal lateinit var mapper: IMapper
    internal lateinit var processor: IProcessor
    internal var interactions: Collection<Interaction> = emptyList()

    abstract fun <TMsg> configureChannel(channel: IChannel<TMsg>): ChortlinConfiguration

    internal abstract fun noChannel(): ChortlinConfiguration
}