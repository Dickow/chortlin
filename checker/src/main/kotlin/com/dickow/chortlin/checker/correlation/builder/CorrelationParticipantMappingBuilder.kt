package com.dickow.chortlin.checker.correlation.builder

import com.dickow.chortlin.checker.correlation.Correlation
import com.dickow.chortlin.checker.correlation.CorrelationParticipantMapping
import java.util.*

class CorrelationParticipantMappingBuilder {
    private val correlations: MutableList<Correlation> = LinkedList()

    fun add(correlation: Correlation): CorrelationParticipantMappingBuilder {
        this.correlations.add(correlation)
        return this
    }

    fun finish(): CorrelationParticipantMapping {
        return CorrelationParticipantMapping(correlations)
    }
}