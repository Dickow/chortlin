package com.dickow.chortlin.core.correlation

import com.dickow.chortlin.core.choreography.participant.ObservableParticipant
import java.util.*

class CorrelationSet(correlations: List<Correlation>) {
    private val correlationSetMappings = Hashtable<ObservableParticipant<*>, Correlation>()

    init {
        for (corr in correlations) {
            correlationSetMappings[corr.participant] = corr
        }
    }

    fun get(participant: ObservableParticipant<*>): Correlation? {
        return correlationSetMappings[participant]
    }
}