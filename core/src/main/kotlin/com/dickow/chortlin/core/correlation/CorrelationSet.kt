package com.dickow.chortlin.core.correlation

import com.dickow.chortlin.core.choreography.participant.Participant
import java.util.*

class CorrelationSet(correlations: List<Correlation>) {
    private val correlationSetMappings = Hashtable<Participant, Correlation>()

    init {
        for (corr in correlations) {
            correlationSetMappings[corr.participant] = corr
        }
    }

    fun get(participant: Participant): Correlation? {
        return correlationSetMappings[participant]
    }
}