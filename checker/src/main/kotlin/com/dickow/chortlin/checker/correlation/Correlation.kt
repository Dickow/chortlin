package com.dickow.chortlin.checker.correlation

import com.dickow.chortlin.checker.trace.Invocation
import com.dickow.chortlin.checker.trace.Return
import com.dickow.chortlin.checker.trace.TraceElement
import com.dickow.chortlin.shared.observation.Observable

class Correlation(
        val observable: Observable,
        private val correlationFunction: CorrelationFunction,
        private val inputFunctions: List<InputTypesFunction>,
        private val returnFunctions: List<ReturnTypesFunction>) {

    fun retrieveKey(arguments: Any?): Any? {
        return correlationFunction.apply(arguments)
    }

    fun getAdditionKeys(trace: TraceElement): Set<CorrelationValue> {
        return when (trace) {
            is Invocation -> {
                inputFunctions.map { func -> func.apply(trace.getArgumentTree()) }.toMutableSet()
            }
            is Return -> {
                returnFunctions.map { func -> func.apply(trace.returnValue) }.toMutableSet()
            }
            else -> return emptySet()
        }
    }

    fun hasInputFunctions(): Boolean {
        return inputFunctions.isNotEmpty()
    }
}