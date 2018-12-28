package com.dickow.chortlin.checker.checker.session

import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.checker.correlation.CorrelationValue
import com.dickow.chortlin.shared.observation.Observable
import com.dickow.chortlin.checker.trace.Trace
import com.dickow.chortlin.checker.trace.TraceElement
import java.util.*

class Session(val sessionId: UUID, val choreography: Choreography, trace: TraceElement) {
    private val traces: MutableList<TraceElement> = LinkedList()
    private val correlationKeys: MutableSet<CorrelationValue> =
            choreography.getCorrelation(trace.getObservation())!!.getAdditionKeys(trace).toMutableSet()

    override fun equals(other: Any?): Boolean {
        return if (other is Session) {
            this.sessionId == other.sessionId
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return sessionId.hashCode()
    }

    fun observedTraces(): Trace {
        return Trace(traces)
    }

    fun correlatesTo(trace: TraceElement): Boolean {
        val key = choreography.getCorrelation(trace.getObservation())?.retrieveKey(trace.getArgumentTree())
        return if (key == null) {
            false
        } else {
            correlationKeys.contains(key)
        }
    }

    fun store(trace: TraceElement) {
        this.traces.add(trace)
    }

    fun hasParticipant(participant: Observable): Boolean {
        return choreography.contains(participant)
    }

    fun extendKeys(trace: TraceElement) {
        correlationKeys.addAll(choreography.getCorrelation(trace.getObservation())!!.getAdditionKeys(trace))
    }
}