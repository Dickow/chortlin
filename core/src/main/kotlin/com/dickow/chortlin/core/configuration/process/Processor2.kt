package com.dickow.chortlin.core.configuration.process

class Processor2<T1, T2, R> constructor(private val processor: (T1, T2) -> R) : IProcessor {

    @Suppress("UNCHECKED_CAST")
    override fun process(args: Array<Any?>): Any? {
        return processor.invoke(args[0] as T1, args[1] as T2)
    }
}