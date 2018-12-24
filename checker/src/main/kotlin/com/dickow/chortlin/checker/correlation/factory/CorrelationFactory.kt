@file:Suppress("UNCHECKED_CAST")

package com.dickow.chortlin.checker.correlation.factory

import com.dickow.chortlin.checker.choreography.method.ObservableMethod
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
    fun correlation(method: ObservableMethod, key: String, correlationFunction: Path): CorrelationBuilder {
        return CorrelationBuilder(method, CorrelationFunction(key, correlationFunction))
    }
}