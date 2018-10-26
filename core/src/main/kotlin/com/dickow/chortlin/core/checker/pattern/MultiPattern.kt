package com.dickow.chortlin.core.checker.pattern

import com.dickow.chortlin.core.trace.Trace

class MultiPattern(
        private val pattern: Pattern,
        previous: Pattern?,
        child: Pattern?)
    : Pattern(previous, child) {

    override fun match(trace: Trace): Boolean {
        return pattern.match(trace) && (child?.match(trace) ?: true)
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