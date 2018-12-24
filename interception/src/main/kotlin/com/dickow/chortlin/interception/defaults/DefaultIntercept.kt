package com.dickow.chortlin.interception.defaults

import com.dickow.chortlin.interception.InterceptStrategy
import com.dickow.chortlin.interception.sending.TraceSender
import com.dickow.chortlin.shared.observation.Observable
import com.dickow.chortlin.shared.transformation.TraceBuilder

class DefaultIntercept(private val sender: TraceSender) : InterceptStrategy {
    private val traceBuilder = TraceBuilder()
    override fun interceptInvocation(observable: Observable, arguments: Array<out Any?>) {
        val traceDTO = traceBuilder.buildInvocationDTO(observable, arguments)
        sender.send(traceDTO)
    }

    override fun interceptReturn(observable: Observable, arguments: Array<out Any?>, returnValue: Any?) {
        val traceDTO = traceBuilder.buildReturnDTO(observable, arguments, returnValue)
        sender.send(traceDTO)
    }
}