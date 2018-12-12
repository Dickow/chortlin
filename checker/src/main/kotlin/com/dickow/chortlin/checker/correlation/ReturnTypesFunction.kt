package com.dickow.chortlin.checker.correlation

import com.dickow.chortlin.shared.trace.Return
import com.dickow.chortlin.shared.trace.TraceElement

class ReturnTypesFunction(private val key: String, private val func: (Any?) -> Any?) : CorrelationFunction() {
    override fun applicableTo(trace: TraceElement): Boolean {
        return trace is Return
    }

    override fun apply(args: Array<Any?>): CorrelationValue {
        return CorrelationValue(key, func(args[0]))
    }
}