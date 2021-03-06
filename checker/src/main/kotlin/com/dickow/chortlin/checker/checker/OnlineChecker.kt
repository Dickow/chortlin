package com.dickow.chortlin.checker.checker

import com.dickow.chortlin.checker.checker.result.CheckResult
import com.dickow.chortlin.checker.checker.result.ChoreographyStatus
import com.dickow.chortlin.checker.checker.session.Session
import com.dickow.chortlin.checker.checker.session.SessionManager
import com.dickow.chortlin.checker.trace.TraceEvent
import com.dickow.chortlin.checker.trace.TraceFactory
import com.dickow.chortlin.shared.exceptions.ChoreographyRuntimeException
import com.dickow.chortlin.shared.trace.protobuf.DtoDefinitions

class OnlineChecker(private val sessionManager: SessionManager) : ChoreographyChecker {
    private val traceBuilder = TraceFactory()
    private val checkLock = Object()

    override fun check(traceDTO: DtoDefinitions.InvocationDTO): ChoreographyStatus {
        val transformed = traceBuilder.buildInvocation(traceDTO)
        return this.check(transformed)
    }

    override fun check(traceDTO: DtoDefinitions.ReturnDTO): ChoreographyStatus {
        val transformed = traceBuilder.buildReturn(traceDTO)
        return this.check(transformed)
    }

    override fun check(trace: TraceEvent): ChoreographyStatus {
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

    private fun checkTraceAgainstSession(session: Session, trace: TraceEvent): ChoreographyStatus {
        val result = check(session, trace)
        return when (result) {
            CheckResult.None -> {
                sessionManager.endSession(session)
                throw ChoreographyRuntimeException("Unexpected trace encountered: $trace ${System.lineSeparator()}"+
                        "The following traces were observed: ${session.observedTraces()}")
            }
            CheckResult.Full -> {sessionManager.endSession(session); ChoreographyStatus.FINISHED}
            CheckResult.Partial -> {session.extendKeys(trace); ChoreographyStatus.IN_PROGRESS}
        }
    }

    private fun check(session: Session, trace: TraceEvent): CheckResult {
        session.store(trace)
        return session.choreography.start.satisfy(session.observedTraces())
    }
}