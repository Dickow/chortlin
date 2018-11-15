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
                    true -> SuccessfulMatch(listOf(element))
                }
            }
            false -> NoMoreTraceMatch()
        }
    }

    fun matchTwo(traces: MutableList<TraceElementIndexed>, expected1: TraceElement, expected2: TraceElement): MatchResult {
        return if (traces.isEmpty()) {
            NoMoreTraceMatch()
        } else {
            val firstElement = traces.first()
            if (firstElement.traceElement == expected1) {
                val secondElement = traces.getOrNull(1)
                return if (secondElement != null && secondElement.traceElement == expected2) {
                    SuccessfulMatch(listOf(firstElement, secondElement))
                } else {
                    if(secondElement == null) PartialMatch(listOf(firstElement)) else InvalidTraceMatch()
                }
            } else {
                InvalidTraceMatch()
            }
        }
    }
}