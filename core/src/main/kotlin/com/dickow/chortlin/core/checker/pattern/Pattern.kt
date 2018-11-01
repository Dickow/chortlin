package com.dickow.chortlin.core.checker.pattern

import com.dickow.chortlin.core.trace.Trace
import com.dickow.chortlin.core.trace.TraceElement
import com.dickow.chortlin.core.trace.TraceElementIndexed

abstract class Pattern(var previous: Pattern?, var child: Pattern?, private var matchedIndex: Int? = null) {
    abstract fun match(trace: Trace) : Boolean

    abstract fun getExpectedTraces(): List<TraceElement>

    protected fun setMatched(element: TraceElementIndexed) {
        this.matchedIndex = element.index
    }

    open fun causalityRespected(t: TraceElementIndexed): Boolean {
        return this.matchedIndex ?: Int.MAX_VALUE < t.index
    }
}