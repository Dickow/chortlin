package com.dickow.chortlin.interception.strategy

import com.dickow.chortlin.shared.trace.TraceElement

class DoNothingStrategy : InterceptStrategy {
    override fun intercept(trace: TraceElement) {
    }
}