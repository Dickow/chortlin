package com.dickow.chortlin.shared.trace

class Trace(private val traces: List<TraceElement>) {
    private var workingTraces = constructWorkingList()

    fun markAllNonConsumed() {
        workingTraces = constructWorkingList()
    }

    fun getNotConsumed(): MutableList<TraceElementIndexed> {
        return workingTraces
    }

    fun consume(element: TraceElementIndexed) {
        workingTraces.remove(element)
    }

    private fun constructWorkingList(): MutableList<TraceElementIndexed> {
        return traces.mapIndexed { index, element -> TraceElementIndexed(index, element) }.toMutableList()
    }
}