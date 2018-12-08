package com.dickow.chortlin.interception.instrumentation.strategy

import com.dickow.chortlin.shared.trace.TraceElement


interface InterceptStrategy {
    fun intercept(trace: TraceElement)
}