package com.dickow.chortlin.checker.correlation

import com.dickow.chortlin.checker.correlation.path.Path
import com.dickow.chortlin.checker.trace.value.Value

class ReturnTypesFunction(private val key: String, private val path: Path) {

    fun apply(returnValue: Value): CorrelationValue {
        val value = path.apply(returnValue)
        return CorrelationValue(key, value)
    }
}