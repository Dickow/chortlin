package com.dickow.chortlin.test.application.shared.builder

import com.dickow.chortlin.checker.trace.Invocation
import com.dickow.chortlin.checker.trace.Return
import com.dickow.chortlin.checker.trace.TraceFactory
import com.dickow.chortlin.interception.dto.TraceDTOFactory
import com.dickow.chortlin.interception.observation.Observation

object TestObservableBuilder {
    private val traceBuilder = TraceFactory()
    private val traceDTOFactory = TraceDTOFactory()

    @JvmStatic
    fun buildInvocation(clazz: Class<*>, methodName: String, arguments: Array<out Any?>): Invocation {
        val method = clazz.methods.single { m -> m.name == methodName }
        return traceBuilder.buildInvocation(traceDTOFactory.buildInvocationDTO(Observation(clazz, method), arguments))
    }

    @JvmStatic
    fun buildReturn(clazz: Class<*>, methodName: String, arguments: Array<out Any?>, returnValue: Any?): Return {
        val method = clazz.methods.single { m -> m.name == methodName }
        return traceBuilder.buildReturn(traceDTOFactory.buildReturnDTO(Observation(clazz, method), arguments, returnValue))
    }
}