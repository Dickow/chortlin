package com.dickow.chortlin.checker.trace

data class Trace(private val traces: List<TraceEvent>) {
    private var workingTraces = constructWorkingList()

    fun getNotConsumed(): MutableList<TraceEventIndexed> {
        return workingTraces
    }

    fun consume(event: TraceEventIndexed) {
        workingTraces.remove(event)
    }

    private fun constructWorkingList(): MutableList<TraceEventIndexed> {
        return traces.mapIndexed { index, element -> TraceEventIndexed(index, element) }.toMutableList()
    }
}