package com.dickow.chortlin.core.configuration.process

class Processor4<T1, T2, T3, T4, R> constructor(private val processor: (T1, T2, T3, T4) -> R) : IProcessor {

    @Suppress("UNCHECKED_CAST")
    override fun process(args: Array<Any>): Any {
        return processor.invoke(args[0] as T1, args[1] as T2, args[2] as T3, args[3] as T4) as Any
    }
}