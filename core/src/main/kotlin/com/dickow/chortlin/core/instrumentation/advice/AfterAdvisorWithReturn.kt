package com.dickow.chortlin.core.instrumentation.advice

import com.dickow.chortlin.core.choreography.participant.observation.ObservedParticipant
import com.dickow.chortlin.core.instrumentation.strategy.InstrumentationStrategy
import com.dickow.chortlin.core.trace.Return
import net.bytebuddy.asm.Advice
import java.lang.reflect.Method

@Suppress("UNUSED_PARAMETER")
class AfterAdvisorWithReturn {
    companion object {
        @JvmStatic
        @Advice.OnMethodExit
        fun afterMethodWithReturnType(@Advice.AllArguments allArguments: Array<Any>, @Advice.Origin method: Method, @Advice.Return returnValue: Any) {
            InstrumentationStrategy.strategy.intercept(Return(ObservedParticipant(method.declaringClass, method), allArguments, returnValue))
        }
    }
}