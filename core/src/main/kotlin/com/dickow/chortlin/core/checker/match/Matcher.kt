package com.dickow.chortlin.core.checker.match

import com.dickow.chortlin.core.trace.TraceElement
import com.dickow.chortlin.core.trace.TraceElementIndexed

class Matcher {

    fun matchOne(traces: MutableList<TraceElementIndexed>, expected: TraceElement): MatchResult {
        return when (traces.isNotEmpty()) {
            true -> {
                val element = traces.firstOrNull()
                when (element?.traceElement == expected) {
                    false -> MatchResult(false, emptyList())
                    true -> MatchResult(true, listOf(element!!))
                }
            }
            false -> MatchResult(false, emptyList())
        }
    }

    fun matchTwo(traces: MutableList<TraceElementIndexed>, expected1: TraceElement, expected2: TraceElement): MatchResult {
        return if (traces.isEmpty() || traces.size < 2) {
            MatchResult(false, emptyList())
        } else {
            val firstElement = traces.firstOrNull()
            if (firstElement != null && firstElement.traceElement == expected1) {
                val secondElement = traces.getOrNull(1)
                return if (secondElement != null && secondElement.traceElement == expected2) {
                    MatchResult(true, listOf(firstElement, secondElement))
                } else {
                    MatchResult(false, emptyList())
                }
            } else {
                MatchResult(false, emptyList())
            }
        }
    }
}