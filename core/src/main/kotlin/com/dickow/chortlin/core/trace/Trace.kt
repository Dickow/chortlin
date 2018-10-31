package com.dickow.chortlin.core.trace

class Trace(private val traces: Array<out TraceElement>) {
    private var workingTraces = constructWorkingList()

    fun markAllNonConsumed() {
        workingTraces = constructWorkingList()
    }

    fun getNotConsumed(): MutableList<TraceElementIndexed> {
        return workingTraces
    }

    fun consume(vararg elements: TraceElementIndexed) {
        workingTraces.removeAll(elements)
    }

    private fun constructWorkingList(): MutableList<TraceElementIndexed> {
        return traces.mapIndexed { index, element -> TraceElementIndexed(index, element) }.toMutableList()
    }
}