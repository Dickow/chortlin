package com.dickow.chortlin.core.checker.pattern

import com.dickow.chortlin.core.trace.Trace
import com.dickow.chortlin.core.trace.TraceElementIndexed

class MultiPattern(
        private val pattern: Pattern,
        previous: Pattern?,
        child: Pattern?)
    : Pattern(previous, child) {

    override fun match(trace: Trace): Boolean {
        return pattern.match(trace) && (child?.match(trace) ?: true)
    }

    override fun causalityRespected(t: TraceElementIndexed): Boolean {
        return this.previous?.causalityRespected(t) ?: true
    }

    override fun equals(other: Any?): Boolean {
        return if (other is MultiPattern) {
            this.pattern == other.pattern
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return pattern.hashCode()
    }
}