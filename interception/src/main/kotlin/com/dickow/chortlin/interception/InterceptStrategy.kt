package com.dickow.chortlin.interception

import com.dickow.chortlin.shared.trace.TraceElement

interface InterceptStrategy {
    fun intercept(trace: TraceElement)
}