package com.dickow.chortlin.core.instrumentation.strategy

import com.dickow.chortlin.core.trace.TraceElement

interface StorageStrategy {
    fun store(trace: TraceElement)
}