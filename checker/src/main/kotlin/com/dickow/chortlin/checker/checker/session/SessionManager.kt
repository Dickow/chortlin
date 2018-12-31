package com.dickow.chortlin.checker.checker.session

import com.dickow.chortlin.checker.trace.TraceElement

interface SessionManager {
    fun getSession(trace: TraceElement): Session?
    fun endSession(session: Session)
    fun beginSession(trace: TraceElement): Session
}