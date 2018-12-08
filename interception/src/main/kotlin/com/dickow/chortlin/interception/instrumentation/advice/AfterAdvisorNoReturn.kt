package com.dickow.chortlin.interception.instrumentation.advice

import com.dickow.chortlin.interception.instrumentation.strategy.InstrumentationStrategy
import com.dickow.chortlin.shared.annotations.ChortlinOnInvoke
import com.dickow.chortlin.shared.observation.Observation
import com.dickow.chortlin.shared.trace.Return
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