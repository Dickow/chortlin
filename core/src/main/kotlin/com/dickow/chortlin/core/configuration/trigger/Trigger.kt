package com.dickow.chortlin.core.configuration.trigger

import com.dickow.chortlin.core.configuration.ChortlinConfiguration
import com.dickow.chortlin.core.configuration.interaction.Interaction
import com.dickow.chortlin.core.configuration.map.IMapper

data class Trigger(internal val endpoint: Any,
                   internal val mapper: IMapper,
                   internal val processor: Any,
                   internal val interactions: Collection<Interaction>,
                   internal val channel: Any) : ChortlinConfiguration