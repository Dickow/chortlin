package com.dickow.chortlin.checker.correlation

import com.dickow.chortlin.checker.correlation.path.Path

class CorrelationFunction(private val key: String, private val path: Path) {
    fun apply(arguments: Any?): CorrelationValue {
        val value = path.apply(arguments)
        return CorrelationValue(key, value)
    }
}