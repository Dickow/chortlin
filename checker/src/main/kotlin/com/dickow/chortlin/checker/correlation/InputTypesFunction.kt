package com.dickow.chortlin.checker.correlation

import com.dickow.chortlin.checker.correlation.path.Path
import com.dickow.chortlin.checker.trace.value.RootValue

class InputTypesFunction(private val key: String, private val path: Path) {

    fun apply(arguments: RootValue): CorrelationValue {
        val value = path.apply(arguments)
        return CorrelationValue(key, value)
    }
}