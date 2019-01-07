package com.dickow.chortlin.checker.trace

import com.dickow.chortlin.checker.trace.value.RootValue
import com.dickow.chortlin.shared.observation.Observable


abstract class TraceElement {
    abstract fun getObservation(): Observable
    abstract fun getArgumentTree(): RootValue
}