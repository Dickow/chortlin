package com.dickow.chortlin.checker.checker

import com.dickow.chortlin.checker.checker.result.CheckResult
import com.dickow.chortlin.checker.checker.session.Session
import com.dickow.chortlin.checker.checker.session.SessionManager
import com.dickow.chortlin.shared.exceptions.ChoreographyRuntimeException
import com.dickow.chortlin.shared.trace.TraceElement
import com.dickow.chortlin.shared.trace.protobuf.DtoDefinitions
import com.dickow.chortlin.shared.transformation.TraceBuilder

class OnlineChecker(private val sessionManager: SessionManager) : ChoreographyChecker {
    private val traceBuilder = TraceBuilder()
    private val checkLock = Object()

    override fun check(traceDTO: DtoDefinitions.InvocationDTO): CheckResult {
        val transformed = traceBuilder.buildInvocation(traceDTO)
        return this.check(transformed)
    }

    override fun check(traceDTO: DtoDefinitions.ReturnDTO): CheckResult {
        val transformed = traceBuilder.buildReturn(traceDTO)
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
            CheckResult.None -> {
                sessionManager.clearSession(session)
                throw ChoreographyRuntimeException("Unexpected trace encountered: $trace ${System.lineSeparator()}"+
                "The following traces were observed prior: ${session.observedTraces()}")
            }
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