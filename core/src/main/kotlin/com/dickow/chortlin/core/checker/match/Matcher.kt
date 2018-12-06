package com.dickow.chortlin.core.checker.match

import com.dickow.chortlin.core.choreography.participant.observation.Observable
import com.dickow.chortlin.core.trace.Invocation
import com.dickow.chortlin.core.trace.Return
import com.dickow.chortlin.core.trace.TraceElementIndexed

class Matcher {

    fun matchReturn(traces: MutableList<TraceElementIndexed>, expected: Observable): MatchResult {
        return when (traces.isNotEmpty()) {
            true -> {
                val element = traces.first()
                if (element.traceElement is Return && element.traceElement.getObservation() == expected) {
                    SuccessfulMatch(element)
                } else {
                    InvalidTraceMatch()
                }
            }
            false -> NoMoreTraceMatch()
        }
    }

    fun matchInvocation(traces: MutableList<TraceElementIndexed>, expected: Observable): MatchResult {
        return when (traces.isNotEmpty()) {
            true -> {
                val element = traces.first()
                if (element.traceElement is Invocation && element.traceElement.getObservation() == expected) {
                    SuccessfulMatch(element)
                } else {
                    InvalidTraceMatch()
                }
            }
            false -> NoMoreTraceMatch()
        }
    }
}