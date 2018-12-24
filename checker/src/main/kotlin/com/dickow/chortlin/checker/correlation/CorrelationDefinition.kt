package com.dickow.chortlin.checker.correlation

import com.dickow.chortlin.shared.observation.Observable
import java.util.*

class CorrelationDefinition(correlations: List<Correlation>) {
    private val correlationSetMappings = Hashtable<Observable, Correlation>()

    init {
        for (corr in correlations) {
            correlationSetMappings[corr.observable] = corr
        }
    }

    fun get(observable: Observable): Correlation? {
        return correlationSetMappings[observable]
    }
}