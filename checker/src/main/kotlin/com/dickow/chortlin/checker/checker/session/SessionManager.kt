package com.dickow.chortlin.checker.checker.session

import com.dickow.chortlin.checker.trace.TraceEvent

interface SessionManager {
    fun getSession(trace: TraceEvent): Session?
    fun endSession(session: Session)
    fun beginSession(trace: TraceEvent): Session
}