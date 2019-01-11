package com.dickow.chortlin.checker.correlation

import com.dickow.chortlin.checker.correlation.path.Path
import com.dickow.chortlin.checker.trace.value.Value

class CorrelationFunction(private val key: String, private val path: Path) {
    fun apply(arguments: Value): CorrelationValue {
        val value = path.apply(arguments)
        return CorrelationValue(key, value)
    }
}