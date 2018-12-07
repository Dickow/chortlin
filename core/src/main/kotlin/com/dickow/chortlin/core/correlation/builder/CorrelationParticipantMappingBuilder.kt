package com.dickow.chortlin.core.correlation.builder

import com.dickow.chortlin.core.correlation.Correlation
import com.dickow.chortlin.core.correlation.CorrelationParticipantMapping
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