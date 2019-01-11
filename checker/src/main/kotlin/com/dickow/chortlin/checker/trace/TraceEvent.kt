package com.dickow.chortlin.checker.trace

import com.dickow.chortlin.checker.trace.value.Value
import com.dickow.chortlin.shared.observation.Observable


abstract class TraceEvent {
    abstract fun getObservation(): Observable
    abstract fun getArgumentTree(): Value
}