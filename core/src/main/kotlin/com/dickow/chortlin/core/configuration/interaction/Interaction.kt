package com.dickow.chortlin.core.configuration.interaction

import com.dickow.chortlin.core.configuration.ChortlinConfiguration
import com.dickow.chortlin.core.configuration.map.IMapper
import com.dickow.chortlin.core.configuration.process.IProcessor
import com.dickow.chortlin.core.message.Message

data class Interaction constructor(
        internal val endpoint: Any,
        internal val mapper: IMapper,
        internal val processor: IProcessor,
        internal val interactions: Collection<Interaction>) : ChortlinConfiguration {

    override fun applyTo(args: Array<Any>) {
        Message(processor.process(arrayOf(mapper.map(emptyArray()))))
    }

    override fun applyTo() {
        Message(processor.process(arrayOf(mapper.map(emptyArray()))))
    }
}