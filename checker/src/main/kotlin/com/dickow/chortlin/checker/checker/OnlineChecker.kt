package com.dickow.chortlin.checker.checker

import com.dickow.chortlin.checker.checker.result.CheckResult
import com.dickow.chortlin.checker.checker.session.Session
import com.dickow.chortlin.checker.checker.session.SessionManager
import com.dickow.chortlin.checker.deserialisation.TraceDeserializer
import com.dickow.chortlin.shared.trace.TraceElement
import com.dickow.chortlin.shared.trace.dto.InvocationDTO
import com.dickow.chortlin.shared.trace.dto.ReturnDTO

class OnlineChecker(private val sessionManager: SessionManager) : ChoreographyChecker {
    private val transform = TraceDeserializer()
    private val checkLock = Object()

    override fun check(traceDTO: InvocationDTO): CheckResult {
        val transformed = transform.deserialize(traceDTO)
        return this.check(transformed)
    }

    override fun check(traceDTO: ReturnDTO): CheckResult {
        val transformed = transform.deserialize(traceDTO)
        return this.check(transformed)
    }

    override fun check(trace: TraceElement): CheckResult {
        synchronized(checkLock) {
            val session = sessionManager.getSession(trace)
            return when (session) {
                null -> {
                    val newSession = sessionManager.beginSession(trace)
                    checkTraceAgainstSession(newSession, trace)
                }
                else -> checkTraceAgainstSession(session, trace)
            }
        }
    }

    private fun checkTraceAgainstSession(session: Session, trace: TraceElement): CheckResult {
        val result = check(session, trace)
        when (result) {
            CheckResult.None -> sessionManager.clearSession(session)
            CheckResult.Full -> sessionManager.clearSession(session)
            CheckResult.Partial -> session.extendKeys(trace)
        }
        return result
    }

    private fun check(session: Session, trace: TraceElement): CheckResult {
        session.store(trace)
        return session.choreography.start.satisfy(session.observedTraces())
    }
}