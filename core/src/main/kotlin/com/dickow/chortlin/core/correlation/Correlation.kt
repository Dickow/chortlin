package com.dickow.chortlin.core.correlation

import com.dickow.chortlin.core.choreography.participant.observation.Observable
import com.dickow.chortlin.core.trace.Invocation
import com.dickow.chortlin.core.trace.Return
import com.dickow.chortlin.core.trace.TraceElement

class Correlation(
        val participant: Observable,
        private val correlationFunction: InputTypesFunction,
        val addFunctions: List<CorrelationFunction>) {

    fun retrieveKey(arguments: Array<Any>): Any {
        return correlationFunction.apply(arguments)
    }

    fun getAdditionKeys(trace: TraceElement): MutableSet<CorrelationValue> {
        val applicableFunctions = addFunctions.filter { func -> func.applicableTo(trace) }
        return when (trace) {
            is Invocation -> {
                applicableFunctions.map { func -> func.apply(trace.getArguments()) }.toMutableSet()
            }
            is Return -> {
                val input = if (trace.returnValue == null) arrayOf(); else arrayOf(trace.returnValue)
                applicableFunctions.map { func -> func.apply(input) }.toMutableSet()
            }
            else -> return mutableSetOf()
        }
    }
}