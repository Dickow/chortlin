package com.dickow.chortlin.checker.correlation

import com.dickow.chortlin.checker.correlation.path.Path

class ReturnTypesFunction(private val key: String, private val path: Path) {

    fun apply(returnValue: Any?): CorrelationValue {
        val value = path.apply(returnValue)
        return CorrelationValue(key, value)
    }
}