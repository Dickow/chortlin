package com.dickow.chortlin.core.checker.pattern

import com.dickow.chortlin.core.trace.Trace
import com.dickow.chortlin.core.trace.TraceElementIndexed

abstract class Pattern(var previous: Pattern?, var child: Pattern?) {
    private var matchedIndex: Int? = null

    abstract fun match(trace: Trace) : Boolean

    protected fun setMatched(element: TraceElementIndexed) {
        this.matchedIndex = element.index
    }

    protected fun causalityRespected(t: TraceElementIndexed): Boolean {
        val previous = this.previous
        return if (previous != null) {
            previous.matchedIndex ?: Int.MAX_VALUE < t.index
        } else {
            true
        }
    }
}