package com.dickow.chortlin.checker.correlation

import com.dickow.chortlin.shared.trace.TraceElement

abstract class CorrelationFunction {
    abstract fun apply(args: Array<Any?>): CorrelationValue
    abstract fun applicableTo(trace: TraceElement): Boolean
}