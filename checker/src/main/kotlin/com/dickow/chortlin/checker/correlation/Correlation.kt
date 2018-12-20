package com.dickow.chortlin.checker.correlation

import com.dickow.chortlin.shared.observation.Observable
import com.dickow.chortlin.shared.trace.Invocation
import com.dickow.chortlin.shared.trace.Return
import com.dickow.chortlin.shared.trace.TraceElement

class Correlation(
        val observable: Observable,
        private val correlationFunction: InputTypesFunction,
        val addFunctions: List<CorrelationFunction>) {

    fun retrieveKey(arguments: Array<Any?>): Any? {
        return correlationFunction.apply(arguments)
    }

    fun getAdditionKeys(trace: TraceElement): Set<CorrelationValue> {
        val applicableFunctions = addFunctions.filter { func -> func.applicableTo(trace) }
        return when (trace) {
            is Invocation -> {
                applicableFunctions.map { func -> func.apply(trace.getArguments()) }.toMutableSet()
            }
            is Return -> {
                val input = if (trace.returnValue == null) arrayOf(); else arrayOf(trace.returnValue)
                applicableFunctions.map { func -> func.apply(input) }.toMutableSet()
            }
            else -> return emptySet()
        }
    }
}