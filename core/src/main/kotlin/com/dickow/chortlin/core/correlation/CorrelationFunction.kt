package com.dickow.chortlin.core.correlation

import com.dickow.chortlin.core.trace.TraceElement

abstract class CorrelationFunction {
    abstract fun apply(args: Array<Any>): Any
    abstract fun applicableTo(trace: TraceElement): Boolean
}