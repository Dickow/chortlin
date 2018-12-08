package com.dickow.chortlin.checker.correlation

import com.dickow.chortlin.shared.trace.Invocation
import com.dickow.chortlin.shared.trace.TraceElement

class InputTypesFunction(private val key: String, private val func: (Array<Any>) -> Any) : CorrelationFunction() {
    override fun applicableTo(trace: TraceElement): Boolean {
        return trace is Invocation
    }

    override fun apply(args: Array<Any>): CorrelationValue {
        return CorrelationValue(key, func(args))
    }
}