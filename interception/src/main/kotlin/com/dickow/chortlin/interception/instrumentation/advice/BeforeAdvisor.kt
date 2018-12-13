package com.dickow.chortlin.interception.instrumentation.advice

import com.dickow.chortlin.interception.configuration.InterceptionStrategy
import com.dickow.chortlin.shared.observation.Observation
import com.dickow.chortlin.shared.trace.Invocation
import net.bytebuddy.asm.Advice
import java.lang.reflect.Method

@Suppress("UNUSED_PARAMETER")
object BeforeAdvisor {
    @JvmStatic
    @Advice.OnMethodEnter
    fun beforeMethod(@Advice.AllArguments allArguments: Array<Any?>, @Advice.Origin method: Method) {
        InterceptionStrategy.strategy.intercept(Invocation(Observation(method.declaringClass, method), allArguments))
    }
}