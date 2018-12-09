package com.dickow.chortlin.shared.trace.dto

import com.dickow.chortlin.shared.trace.TraceElement

class TraceElementDTO constructor() {
    constructor(trace: TraceElement) : this(){
        classCanonicalName = trace.getObservation().clazz.canonicalName
        methodCanonicalName = trace.getObservation().method.name
        arguments = trace.getArguments()
    }

    private lateinit var classCanonicalName: String
    private lateinit var methodCanonicalName: String
    private lateinit var arguments : Array<Any?>
}