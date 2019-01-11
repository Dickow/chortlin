package com.dickow.chortlin.checker.checker.session

import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.checker.correlation.CorrelationValue
import com.dickow.chortlin.checker.trace.Trace
import com.dickow.chortlin.checker.trace.TraceEvent
import com.dickow.chortlin.shared.observation.Observable
import java.util.*

class Session(val sessionId: UUID, val choreography: Choreography, trace: TraceEvent) {
    private val traces: MutableList<TraceEvent> = LinkedList()
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

    fun correlatesTo(trace: TraceEvent): Boolean {
        val key = choreography.getCorrelation(trace.getObservation())?.retrieveKey(trace.getArgumentTree())
        return if (key == null) {
            false
        } else {
            correlationKeys.contains(key)
        }
    }

    fun store(trace: TraceEvent) {
        this.traces.add(trace)
    }

    fun containsObservable(observable: Observable): Boolean {
        return choreography.contains(observable)
    }

    fun extendKeys(trace: TraceEvent) {
        correlationKeys.addAll(choreography.getCorrelation(trace.getObservation())!!.getAdditionKeys(trace))
    }
}