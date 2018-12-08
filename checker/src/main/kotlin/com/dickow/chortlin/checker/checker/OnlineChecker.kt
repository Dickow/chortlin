package com.dickow.chortlin.checker.checker

import com.dickow.chortlin.checker.checker.result.CheckResult
import com.dickow.chortlin.checker.checker.session.Session
import com.dickow.chortlin.checker.checker.session.SessionManager
import com.dickow.chortlin.shared.trace.TraceElement

class OnlineChecker(private val sessionManager: SessionManager) {
    private val checkLock = Object()

    fun check(trace: TraceElement): CheckResult {
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
        val result = session.checkNewTrace(trace)
        when (result) {
            CheckResult.None -> sessionManager.clearSession(session)
            CheckResult.Full -> sessionManager.clearSession(session)
            CheckResult.Partial -> {
                session.extendKeySet(trace)
            }
        }
        return result
    }
}