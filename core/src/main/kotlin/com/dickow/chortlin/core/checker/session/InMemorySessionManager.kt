package com.dickow.chortlin.core.checker.session

import com.dickow.chortlin.core.checker.ParticipantRetriever
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.observation.Observable
import com.dickow.chortlin.core.exceptions.ChortlinRuntimeException
import com.dickow.chortlin.core.trace.TraceElement
import java.util.*

class InMemorySessionManager(choreographies: List<Choreography>) : SessionManager {
    private val ongoingSessions: MutableMap<UUID, Session> = Hashtable()
    private val participantToChoreographyMap = Hashtable<Observable, Choreography>()

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

    override fun beginSession(trace: TraceElement): Session {
        val choreography = participantToChoreographyMap[trace.getObservation()]
        if (choreography != null) {
            val sessionId = UUID.randomUUID()
            val session = Session(sessionId, choreography, trace)
            ongoingSessions[sessionId] = session
            return session

        } else {
            throw ChortlinRuntimeException("Unable to find a possible choreography instance for trace: $trace")
        }
    }

    override fun getSession(trace: TraceElement): Session? {
        return ongoingSessions.values.find { session ->
            session.correlatesTo(trace) && session.hasParticipant(trace.getObservation())
        }
    }
}