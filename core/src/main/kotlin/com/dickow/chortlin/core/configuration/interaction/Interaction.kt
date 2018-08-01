package com.dickow.chortlin.core.configuration.interaction

import com.dickow.chortlin.core.configuration.ChortlinConfiguration
import com.dickow.chortlin.core.configuration.map.IMapper

data class Interaction(
        internal val endpoint: Any,
        internal val mapper: IMapper,
        internal val processor: Any,
        internal val interactions: Collection<Interaction>,
        internal val channel: Any) : ChortlinConfiguration {

    override fun hashCode(): Int {
        return endpoint.hashCode()
    }
}