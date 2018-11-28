package com.dickow.chortlin.core.correlation

import com.dickow.chortlin.core.choreography.participant.ObservableParticipant

class Correlation(
        val participant: ObservableParticipant<*>,
        val correlationFunction: InputTypesFunction,
        vararg val addFunctions: CorrelationFunction) {
    fun retrieveKey(arguments: Array<Any>): Any {
        return correlationFunction.apply(arguments)
    }
}