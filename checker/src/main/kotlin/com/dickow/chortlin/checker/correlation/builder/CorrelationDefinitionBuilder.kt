package com.dickow.chortlin.checker.correlation.builder

import com.dickow.chortlin.checker.correlation.Correlation
import com.dickow.chortlin.checker.correlation.CorrelationDefinition
import java.util.*

class CorrelationDefinitionBuilder {
    private val correlations: MutableList<Correlation> = LinkedList()

    fun add(correlation: Correlation): CorrelationDefinitionBuilder {
        this.correlations.add(correlation)
        return this
    }

    fun finish(): CorrelationDefinition {
        return CorrelationDefinition(correlations)
    }
}