package com.dickow.chortlin.core.trace

class Trace(private val traces: Array<TraceElement<*>>) {
    private var workingTraces = mutableListOf(*traces)

    fun markAllNonConsumed() {
        workingTraces = mutableListOf(*traces)
    }

    fun getNotConsumed(): MutableList<TraceElement<*>> {
        return workingTraces
    }

    fun consume(count: Int) {
        if (workingTraces.size > count) {
            workingTraces = workingTraces.subList(count, workingTraces.size)
        } else {
            workingTraces.clear()
        }
    }
}