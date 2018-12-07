package com.dickow.chortlin.core.correlation

import com.dickow.chortlin.core.trace.Invocation
import com.dickow.chortlin.core.trace.TraceElement

class InputTypesFunction(private val key: String, private val func: (Array<Any>) -> Any) : CorrelationFunction() {
    override fun applicableTo(trace: TraceElement): Boolean {
        return trace is Invocation
    }

    override fun apply(args: Array<Any>): CorrelationValue {
        return CorrelationValue(key, func(args))
    }
}