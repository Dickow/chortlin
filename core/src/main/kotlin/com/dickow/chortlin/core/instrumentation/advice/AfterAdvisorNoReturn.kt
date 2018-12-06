package com.dickow.chortlin.core.instrumentation.advice

import com.dickow.chortlin.core.choreography.participant.observation.Observation
import com.dickow.chortlin.core.instrumentation.strategy.InstrumentationStrategy
import com.dickow.chortlin.core.trace.Return
import net.bytebuddy.asm.Advice
import java.lang.reflect.Method

@Suppress("UNUSED_PARAMETER")
class AfterAdvisorNoReturn {
    companion object {
        @JvmStatic
        @Advice.OnMethodExit
        fun afterMethodNoReturnType(@Advice.AllArguments allArguments: Array<Any>, @Advice.Origin method: Method) {
            InstrumentationStrategy.strategy.intercept(Return(Observation(method.declaringClass, method), allArguments, null))
        }
    }
}