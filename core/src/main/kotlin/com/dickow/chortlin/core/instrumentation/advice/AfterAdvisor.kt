package com.dickow.chortlin.core.instrumentation.advice

import com.dickow.chortlin.core.choreography.participant.Participant
import com.dickow.chortlin.core.instrumentation.strategy.InstrumentationStrategy
import com.dickow.chortlin.core.trace.Return
import net.bytebuddy.asm.Advice
import java.lang.reflect.Method

@Suppress("UNUSED_PARAMETER")
class AfterAdvisor {
    companion object {
        @JvmStatic
        @Advice.OnMethodExit
        fun afterMethod(@Advice.AllArguments allArguments: Array<Any>, @Advice.Origin method: Method) {
            println("After invocation of ${method.name}")
            InstrumentationStrategy.strategy.store(Return(Participant(method.declaringClass, method)))
        }
    }
}