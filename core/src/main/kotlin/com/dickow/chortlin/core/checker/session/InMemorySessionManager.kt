package com.dickow.chortlin.core.checker.session

import com.dickow.chortlin.core.checker.ParticipantRetriever
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ObservableParticipant
import com.dickow.chortlin.core.exceptions.ChortlinRuntimeException
import com.dickow.chortlin.core.trace.TraceElement
import java.util.*

class InMemorySessionManager(choreographies: List<Choreography>) : SessionManager {
    private val ongoingSessions: MutableMap<UUID, Session> = Hashtable()
    private val participantToChoreographyMap = Hashtable<ObservableParticipant<*>, Choreography>()

    init {
        choreographies.forEach { choreography ->
            val participantVisitor = ParticipantRetriever()
            choreography.runVisitor(participantVisitor)
            participantVisitor.getParticipants().forEach { participant ->
                participantToChoreographyMap[participant] = choreography
            }
        }
    }

    override fun clearSession(session: Session) {
        ongoingSessions.remove(session.sessionId)
    }

    override fun beginSession(trace: TraceElement) {
        val choreography = participantToChoreographyMap[trace.getParticipant()]
        if (choreography != null) {
            val sessionId = UUID.randomUUID()
            ongoingSessions[sessionId] = Session(sessionId, choreography)
        } else {
            throw ChortlinRuntimeException("Unable to find a possible choreography instance for trace: $trace")
        }
    }

    override fun getSession(trace: TraceElement): Session? {
        return ongoingSessions.values.find { session -> session.participantSet.contains(trace.getParticipant()) }
    }
}