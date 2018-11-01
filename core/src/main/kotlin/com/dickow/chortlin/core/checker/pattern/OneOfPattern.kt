package com.dickow.chortlin.core.checker.pattern

import com.dickow.chortlin.core.checker.match.Matcher
import com.dickow.chortlin.core.trace.Trace
import com.dickow.chortlin.core.trace.TraceElement

class OneOfPattern(
        private val possiblePatterns: List<Pattern>,
        previous: Pattern?,
        child: Pattern?) : Pattern(previous, child) {
    private val matcher = Matcher()

    override fun getExpectedTraces(): List<TraceElement> {
        return possiblePatterns.map { it.getExpectedTraces() }.flatten()
    }

    override fun match(trace: Trace): Boolean {
        val chosenPattern = possiblePatterns.firstOrNull { pattern ->
            pattern.getExpectedTraces().any { t ->
                matcher.matchOne(trace.getNotConsumed(), t) { tr ->
                    previous?.causalityRespected(tr) ?: true
                }.matched
            }
        }
        return chosenPattern?.match(trace) ?: false
    }
}