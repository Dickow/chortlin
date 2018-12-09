package com.dickow.chortlin.interception.instrumentation.advice

import com.dickow.chortlin.interception.instrumentation.strategy.InstrumentationStrategy
import com.dickow.chortlin.shared.observation.Observation
import com.dickow.chortlin.shared.trace.Invocation
import net.bytebuddy.asm.Advice
import java.lang.reflect.Method

@Suppress("UNUSED_PARAMETER")
class BeforeAdvisor {
    companion object {
        @JvmStatic
        @Advice.OnMethodEnter
        fun beforeMethod(@Advice.AllArguments allArguments: Array<Any?>, @Advice.Origin method: Method) {
            InstrumentationStrategy.strategy.intercept(Invocation(Observation(method.declaringClass, method), allArguments))
        }
    }

}