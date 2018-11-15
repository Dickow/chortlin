package com.dickow.chortlin.core.checker

import com.dickow.chortlin.core.checker.result.CheckResult
import com.dickow.chortlin.core.checker.session.SessionManager
import com.dickow.chortlin.core.trace.TraceElement

class OnlineChecker(private val sessionManager: SessionManager) {
    private val checkLock = Object()

    fun check(trace: TraceElement): CheckResult {
        synchronized(checkLock) {
            val session = sessionManager.getSession(trace)
            return if (session != null) {
                val result = session.checkNewTrace(trace)
                when (result) {
                    CheckResult.None -> sessionManager.clearSession(session)
                    CheckResult.Full -> sessionManager.clearSession(session)
                    CheckResult.Partial -> {
                    }
                }
                result
            } else {
                sessionManager.beginSession(trace)
                this.check(trace)
            }
        }
    }
}