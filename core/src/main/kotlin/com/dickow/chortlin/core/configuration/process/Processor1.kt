package com.dickow.chortlin.core.configuration.process

class Processor1<T1, R> constructor(private val processor: (T1) -> R) : IProcessor {

    @Suppress("UNCHECKED_CAST")
    override fun process(args: Array<Any?>): Any? {
        return processor.invoke(args[0] as T1)
    }
}