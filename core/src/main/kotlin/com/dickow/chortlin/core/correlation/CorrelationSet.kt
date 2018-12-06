package com.dickow.chortlin.core.correlation

import com.dickow.chortlin.core.choreography.participant.observation.Observable
import java.util.*

class CorrelationSet(correlations: List<Correlation>) {
    private val correlationSetMappings = Hashtable<Observable, Correlation>()

    init {
        for (corr in correlations) {
            correlationSetMappings[corr.participant] = corr
        }
    }

    fun get(participant: Observable): Correlation? {
        return correlationSetMappings[participant]
    }
}