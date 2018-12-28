package com.dickow.chortlin.interception.defaults

import com.dickow.chortlin.interception.InterceptStrategy
import com.dickow.chortlin.interception.dto.TraceDTOFactory
import com.dickow.chortlin.interception.observation.Observation
import com.dickow.chortlin.interception.sending.TraceSender

class DefaultInterceptor(private val sender: TraceSender) : InterceptStrategy {
    private val traceDTOFactory = TraceDTOFactory()

    override fun interceptInvocation(observation: Observation, arguments: Array<out Any?>) {
        val traceDTO = traceDTOFactory.buildInvocationDTO(observation, arguments)
        sender.send(traceDTO)
    }

    override fun interceptReturn(observation: Observation, arguments: Array<out Any?>, returnValue: Any?) {
        val traceDTO = traceDTOFactory.buildReturnDTO(observation, arguments, returnValue)
        sender.send(traceDTO)
    }
}