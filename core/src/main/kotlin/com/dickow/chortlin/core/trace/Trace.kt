package com.dickow.chortlin.core.trace

class Trace(val traces: Array<TraceElement<*>>) : Iterable<TraceElement<*>> {
    override fun iterator(): Iterator<TraceElement<*>> {
        return traces.iterator()
    }
}