package com.dickow.chortlin.core.checker.match

import com.dickow.chortlin.core.choreography.participant.ObservableParticipant
import com.dickow.chortlin.core.trace.Invocation
import com.dickow.chortlin.core.trace.Return
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

    fun <T> matchReturn(traces: MutableList<TraceElementIndexed>, expected: ObservableParticipant<T>): MatchResult {
        return when (traces.isNotEmpty()) {
            true -> {
                val element = traces.first()
                if (element.traceElement is Return && element.traceElement.getParticipant() == expected) {
                    SuccessfulMatch(element)
                } else {
                    InvalidTraceMatch()
                }
            }
            false -> NoMoreTraceMatch()
        }
    }

    fun <T> matchInvocation(traces: MutableList<TraceElementIndexed>, expected: ObservableParticipant<T>): MatchResult {
        return when (traces.isNotEmpty()) {
            true -> {
                val element = traces.first()
                if (element.traceElement is Invocation && element.traceElement.getParticipant() == expected) {
                    SuccessfulMatch(element)
                } else {
                    InvalidTraceMatch()
                }
            }
            false -> NoMoreTraceMatch()
        }
    }
}