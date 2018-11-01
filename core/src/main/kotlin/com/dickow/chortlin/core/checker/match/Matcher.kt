package com.dickow.chortlin.core.checker.match

import com.dickow.chortlin.core.trace.TraceElement
import com.dickow.chortlin.core.trace.TraceElementIndexed

class Matcher {

    fun matchOne(
            traces: MutableList<TraceElementIndexed>,
            expected: TraceElement,
            causalityFunc: (TraceElementIndexed) -> Boolean): MatchResult {
        return when (traces.isNotEmpty()) {
            true -> {
                val element = traces.firstOrNull { t -> t.traceElement == expected && causalityFunc(t) }
                when (element) {
                    null -> MatchResult(false, emptyList())
                    else -> MatchResult(true, listOf(element))
                }
            }
            false -> MatchResult(false, emptyList())
        }
    }

    fun matchTwo(
            traces: MutableList<TraceElementIndexed>,
            expected1: TraceElement,
            expected2: TraceElement,
            causalityFunc: (TraceElementIndexed) -> Boolean): MatchResult {
        return if (traces.isEmpty() || traces.size < 2) {
            MatchResult(false, emptyList())
        } else {
            val firstElement = traces.firstOrNull { t -> t.traceElement == expected1 && causalityFunc(t) }
            if (firstElement != null) {
                val secondElement = traces.firstOrNull { t ->
                    t.traceElement == expected2 && t.index > firstElement.index
                }
                return if (secondElement != null) {
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