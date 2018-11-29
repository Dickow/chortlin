package com.dickow.chortlin.core.correlation

import com.dickow.chortlin.core.choreography.participant.ObservableParticipant
import com.dickow.chortlin.core.trace.Return
import com.dickow.chortlin.core.trace.TraceElement
import com.dickow.chortlin.core.util.TypeUtil
import java.lang.reflect.Method

class ReturnTypesFunction(private val func: (Any) -> Any, private val underlyingMethods: Array<Method>) : CorrelationFunction() {
    override fun applicableTo(trace: TraceElement): Boolean {
        return this.applicableTo(trace.getParticipant()) && trace is Return
    }

    override fun applicableTo(participant: ObservableParticipant<*>): Boolean {
        return TypeUtil.getMethodMatchForReturnType(participant.method.returnType, underlyingMethods) != null
    }

    override fun apply(args: Array<Any>): Any {
        return func(args[0])
    }
}