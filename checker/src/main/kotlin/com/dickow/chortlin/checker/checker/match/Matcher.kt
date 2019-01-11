package com.dickow.chortlin.checker.checker.match

import com.dickow.chortlin.checker.trace.Invocation
import com.dickow.chortlin.checker.trace.Return
import com.dickow.chortlin.checker.trace.TraceEventIndexed
import com.dickow.chortlin.shared.observation.Observable

class Matcher {

    fun matchReturn(traces: MutableList<TraceEventIndexed>, expected: Observable): MatchResult {
        return when (traces.isNotEmpty()) {
            true -> {
                val element = traces.first()
                if (element.traceEvent is Return && element.traceEvent.getObservation() == expected) {
                    SuccessfulMatch(element)
                } else {
                    InvalidTraceMatch()
                }
            }
            false -> NoMoreTraceMatch()
        }
    }

    fun matchInvocation(traces: MutableList<TraceEventIndexed>, expected: Observable): MatchResult {
        return when (traces.isNotEmpty()) {
            true -> {
                val element = traces.first()
                if (element.traceEvent is Invocation && element.traceEvent.getObservation() == expected) {
                    SuccessfulMatch(element)
                } else {
                    InvalidTraceMatch()
                }
            }
            false -> NoMoreTraceMatch()
        }
    }
}