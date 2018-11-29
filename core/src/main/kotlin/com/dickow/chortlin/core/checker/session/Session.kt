package com.dickow.chortlin.core.checker.session

import com.dickow.chortlin.core.checker.ChoreographyChecker
import com.dickow.chortlin.core.checker.ParticipantRetriever
import com.dickow.chortlin.core.checker.result.CheckResult
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ObservableParticipant
import com.dickow.chortlin.core.trace.Trace
import com.dickow.chortlin.core.trace.TraceElement
import java.util.*

class Session(val sessionId: UUID, val choreography: Choreography, trace: TraceElement) {
    private val checker: ChoreographyChecker = choreography.createChecker()
    private val traces: MutableList<TraceElement> = LinkedList()
    private val participantSet: Set<ObservableParticipant<*>>
    private val correlationKeys: MutableSet<Any>

    init {
        val participantVisitor = ParticipantRetriever()
        choreography.runVisitor(participantVisitor)
        participantSet = participantVisitor.getParticipants()
        correlationKeys = choreography.getCorrelation(trace.getParticipant())!!.getAdditionKeys(trace)
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
        val key = choreography.getCorrelation(trace.getParticipant())!!.retrieveKey(trace.getArguments())
        return correlationKeys.contains(key)
    }

    fun hasParticipant(participant: ObservableParticipant<*>): Boolean {
        return participantSet.contains(participant)
    }

    fun extendKeySet(trace: TraceElement) {
        correlationKeys.addAll(choreography.getCorrelation(trace.getParticipant())!!.getAdditionKeys(trace))
    }
}