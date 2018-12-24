package com.dickow.chortlin.shared.trace

import com.dickow.chortlin.shared.observation.Observation


abstract class TraceElement {
    abstract fun getObservation(): Observation
    abstract fun getArgumentTree(): Map<*, *>
}