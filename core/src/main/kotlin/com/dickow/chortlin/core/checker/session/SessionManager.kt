package com.dickow.chortlin.core.checker.session

import com.dickow.chortlin.core.trace.TraceElement

interface SessionManager {
    fun getSession(trace: TraceElement): Session?
    fun clearSession(session: Session)
    fun beginSession(trace: TraceElement): Session
}