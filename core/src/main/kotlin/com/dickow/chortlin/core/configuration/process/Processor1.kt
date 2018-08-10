package com.dickow.chortlin.core.configuration.process

class Processor1<T1, R> constructor(private val processor: (T1) -> R) : IProcessor {

    @Suppress("UNCHECKED_CAST")
    override fun process(arg: Any?): Any? {
        return processor.invoke(arg as T1)
    }
}