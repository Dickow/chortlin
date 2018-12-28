package com.dickow.chortlin.core.test.shared.builder

import com.dickow.chortlin.shared.observation.Observable
import com.dickow.chortlin.shared.trace.Invocation
import com.dickow.chortlin.shared.trace.Return
import com.dickow.chortlin.shared.transformation.TraceBuilder

object TestObservableBuilder {
    private val traceBuilder = TraceBuilder()

    @JvmStatic
    fun buildInvocation(observable: Observable, arguments: Array<Any?>): Invocation {
        return traceBuilder.buildInvocation(traceBuilder.buildInvocationDTO(observable, arguments))
    }

    @JvmStatic
    fun buildReturn(observable: Observable, arguments: Array<Any?>, returnValue: Any?): Return {
        return traceBuilder.buildReturn(traceBuilder.buildReturnDTO(observable, arguments, returnValue))
    }
}