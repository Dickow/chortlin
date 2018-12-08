package com.dickow.chortlin.interception.instrumentation.strategy

import com.dickow.chortlin.shared.trace.TraceElement
import java.util.*

class StoreInMemory : InterceptStrategy {
    private val traces: MutableList<TraceElement> = LinkedList()
    override fun intercept(trace: TraceElement) {
        synchronized(this) { traces.add(trace) }
    }
}