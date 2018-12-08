package com.dickow.chortlin.checker.correlation

import com.dickow.chortlin.shared.observation.Observable
import java.util.*


class CorrelationParticipantMapping(correlations: List<Correlation>) {
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