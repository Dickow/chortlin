package com.dickow.chortlin.core.instrumentation.strategy

import com.dickow.chortlin.core.trace.TraceElement

interface InterceptStrategy {
    fun intercept(trace: TraceElement)
}