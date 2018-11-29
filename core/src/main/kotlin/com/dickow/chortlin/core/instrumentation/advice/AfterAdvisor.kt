package com.dickow.chortlin.core.instrumentation.advice

import com.dickow.chortlin.core.choreography.participant.ObservableParticipant
import com.dickow.chortlin.core.instrumentation.strategy.InstrumentationStrategy
import com.dickow.chortlin.core.trace.Return
import net.bytebuddy.asm.Advice
import java.lang.reflect.Method

@Suppress("UNUSED_PARAMETER")
class AfterAdvisor {
    companion object {
        @JvmStatic
        @Advice.OnMethodExit
        fun afterMethod(@Advice.AllArguments allArguments: Array<Any>, @Advice.Origin method: Method, @Advice.Return returnValue: Any) {
            InstrumentationStrategy.strategy.intercept(Return(ObservableParticipant(method.declaringClass, method), allArguments, returnValue))
        }
    }
}