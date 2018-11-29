package com.dickow.chortlin.core.correlation

import com.dickow.chortlin.core.choreography.participant.ObservableParticipant
import com.dickow.chortlin.core.trace.Invocation
import com.dickow.chortlin.core.trace.Return
import com.dickow.chortlin.core.trace.TraceElement

class Correlation(
        val participant: ObservableParticipant<*>,
        val correlationFunction: InputTypesFunction,
        val addFunctions: List<CorrelationFunction>) {

    fun retrieveKey(arguments: Array<Any>): Any {
        return correlationFunction.apply(arguments)
    }

    fun getAdditionKeys(trace: TraceElement): MutableSet<Any> {
        val applicableFunctions = addFunctions.filter { func -> func.applicableTo(trace) }
        return when (trace) {
            is Invocation -> {
                applicableFunctions.map { func -> func.apply(trace.getArguments()) }.toMutableSet()
            }
            is Return -> {
                applicableFunctions.map { func -> func.apply(arrayOf(trace.returnValue)) }.toMutableSet()
            }
            else -> return mutableSetOf()
        }
    }
}