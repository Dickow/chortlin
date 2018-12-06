package com.dickow.chortlin.core.trace

import com.dickow.chortlin.core.choreography.participant.observation.Observation

abstract class TraceElement {
    abstract fun getObservation(): Observation
    abstract fun getArguments(): Array<Any>
}