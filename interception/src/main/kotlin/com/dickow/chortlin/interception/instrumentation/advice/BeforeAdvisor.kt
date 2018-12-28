package com.dickow.chortlin.interception.instrumentation.advice

import com.dickow.chortlin.interception.configuration.InterceptionStrategy
import com.dickow.chortlin.interception.observation.Observation
import net.bytebuddy.asm.Advice
import java.lang.reflect.Method

object BeforeAdvisor {
    @JvmStatic
    @Advice.OnMethodEnter
    fun beforeMethod(@Advice.AllArguments allArguments: Array<Any?>, @Advice.Origin method: Method) {
        InterceptionStrategy.strategy.interceptInvocation(Observation(method.declaringClass, method), allArguments)
    }
}