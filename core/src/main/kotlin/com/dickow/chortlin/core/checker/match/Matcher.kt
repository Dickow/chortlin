package com.dickow.chortlin.core.checker.match

import com.dickow.chortlin.core.trace.TraceElement
import com.dickow.chortlin.core.trace.TraceElementIndexed

class Matcher {

    fun matchOne(traces: MutableList<TraceElementIndexed>, expected: TraceElement): MatchResult {
        return when (traces.isNotEmpty()) {
            true -> {
                val element = traces.first()
                when (element.traceElement == expected) {
                    false -> InvalidTraceMatch()
                    true -> SuccessfulMatch(element)
                }
            }
            false -> NoMoreTraceMatch()
        }
    }
}