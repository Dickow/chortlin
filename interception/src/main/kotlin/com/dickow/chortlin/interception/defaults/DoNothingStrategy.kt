package com.dickow.chortlin.interception.defaults

import com.dickow.chortlin.interception.InterceptStrategy
import com.dickow.chortlin.shared.trace.TraceElement

class DoNothingStrategy : InterceptStrategy {
    override fun intercept(trace: TraceElement) {
    }
}