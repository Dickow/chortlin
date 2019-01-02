@file:Suppress("UNCHECKED_CAST")

package com.dickow.chortlin.checker.correlation.factory

import com.dickow.chortlin.checker.choreography.participant.ObservableParticipant
import com.dickow.chortlin.checker.correlation.CorrelationFunction
import com.dickow.chortlin.checker.correlation.builder.CorrelationBuilder
import com.dickow.chortlin.checker.correlation.builder.CorrelationParticipantMappingBuilder
import com.dickow.chortlin.checker.correlation.path.Path

object CorrelationFactory {

    @JvmStatic
    fun defineCorrelation(): CorrelationParticipantMappingBuilder {
        return CorrelationParticipantMappingBuilder()
    }

    @JvmStatic
    fun correlation(observable: ObservableParticipant, key: String, correlationFunction: Path): CorrelationBuilder {
        return CorrelationBuilder(observable, CorrelationFunction(key, correlationFunction))
    }
}