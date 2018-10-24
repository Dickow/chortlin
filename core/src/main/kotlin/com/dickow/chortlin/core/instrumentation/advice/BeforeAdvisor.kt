package com.dickow.chortlin.core.instrumentation.advice

import com.dickow.chortlin.core.choreography.participant.Participant
import com.dickow.chortlin.core.instrumentation.strategy.InstrumentationStrategy
import com.dickow.chortlin.core.trace.TraceElement
import net.bytebuddy.asm.Advice
import java.lang.reflect.Method

@Suppress("UNUSED_PARAMETER")
class BeforeAdvisor {
    companion object {
        @JvmStatic
        @Advice.OnMethodEnter
        fun beforeMethod(@Advice.AllArguments allArguments: Array<Any>, @Advice.Origin method: Method) {
            println("Before invocation of ${method.name}")
            InstrumentationStrategy.strategy.store(TraceElement(Participant(method.declaringClass, method)))
        }
    }

}