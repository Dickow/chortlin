package com.dickow.chortlin.core.instrumentation.strategy

import com.dickow.chortlin.core.trace.TraceElement
import java.util.*

class InMemory : StorageStrategy {
    private val traces: MutableList<TraceElement> = LinkedList()
    override fun store(trace: TraceElement) {
        synchronized(this) { traces.add(trace) }
    }
}