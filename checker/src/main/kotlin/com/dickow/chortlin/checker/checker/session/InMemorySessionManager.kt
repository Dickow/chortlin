package com.dickow.chortlin.checker.checker.session

import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.checker.trace.TraceElement
import com.dickow.chortlin.shared.exceptions.ChoreographyRuntimeException
import java.util.*


class InMemorySessionManager(private val choreographies: List<Choreography>) : SessionManager {
    private val activeSessions: MutableMap<UUID, Session> = Hashtable()

    override fun endSession(session: Session) {
        activeSessions.remove(session.sessionId)
    }

    override fun beginSession(trace: TraceElement): Session {
        val choreography = choreographies.firstOrNull { c -> c.contains(trace.getObservation()) }
        if (choreography != null) {
            val sessionId = UUID.randomUUID()
            val session = Session(sessionId, choreography, trace)
            activeSessions[sessionId] = session
            return session
        } else {
            throw ChoreographyRuntimeException("Unable to find a possible choreography instance for trace: $trace")
        }
    }

    override fun getSession(trace: TraceElement): Session? {
        return activeSessions.values.find { session ->
            session.correlatesTo(trace) && session.containsObservable(trace.getObservation())
        }
    }
}