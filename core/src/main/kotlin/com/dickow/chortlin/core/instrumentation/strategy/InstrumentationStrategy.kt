package com.dickow.chortlin.core.instrumentation.strategy

object InstrumentationStrategy {
    @JvmStatic
    var strategy: StorageStrategy = defaultStrategy()

    fun defaultStrategy(): StorageStrategy {
        return InMemory()
    }
}