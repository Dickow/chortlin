package com.dickow.chortlin.core.configuration

import com.dickow.chortlin.core.api.endpoint.Endpoint
import com.dickow.chortlin.core.configuration.interaction.Interaction
import com.dickow.chortlin.core.configuration.map.IMapper
import com.dickow.chortlin.core.configuration.process.IProcessor

abstract class Definition {
    internal lateinit var endpoint: Endpoint
    internal lateinit var mapper: IMapper
    internal lateinit var processor: IProcessor
    internal var interactions: Collection<Interaction> = emptyList()

    abstract fun <TMsg> configureChannel(channel: IChannel<TMsg>): ChortlinConfiguration

    abstract fun noChannel(): ChortlinConfiguration
}