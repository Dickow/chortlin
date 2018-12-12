package com.dickow.chortlin.checker.checker.session

import com.dickow.chortlin.checker.checker.ChoreographyChecker
import com.dickow.chortlin.checker.checker.ParticipantRetriever
import com.dickow.chortlin.checker.checker.factory.CheckerFactory
import com.dickow.chortlin.checker.checker.result.CheckResult
import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.checker.correlation.CorrelationValue
import com.dickow.chortlin.shared.observation.Observable
import com.dickow.chortlin.shared.observation.ObservableParticipant
import com.dickow.chortlin.shared.trace.Trace
import com.dickow.chortlin.shared.trace.TraceElement
import java.util.*

class Session(val sessionId: UUID, val choreography: Choreography, trace: TraceElement) {
    private val checker: ChoreographyChecker = CheckerFactory.createChecker(choreography)
    private val traces: MutableList<TraceElement> = LinkedList()
    private val participantSet: Set<ObservableParticipant>
    private val correlationKeys: MutableSet<CorrelationValue>

    init {
        val participantVisitor = ParticipantRetriever()
        choreography.runVisitor(participantVisitor)
        participantSet = participantVisitor.getParticipants()
        correlationKeys = choreography.getCorrelation(trace.getObservation())!!.getAdditionKeys(trace)
    }

    fun checkNewTrace(trace: TraceElement): CheckResult {
        traces.add(trace)
        return checker.check(Trace(traces))
    }

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

    fun correlatesTo(trace: TraceElement): Boolean {
        val key = choreography.getCorrelation(trace.getObservation())?.retrieveKey(trace.getArguments())
        return if (key == null) {
            false
        } else {
            correlationKeys.contains(key)
        }
    }

    fun hasParticipant(participant: Observable): Boolean {
        return participantSet.contains(participant)
    }

    fun extendKeySet(trace: TraceElement) {
        correlationKeys.addAll(choreography.getCorrelation(trace.getObservation())!!.getAdditionKeys(trace))
    }
}