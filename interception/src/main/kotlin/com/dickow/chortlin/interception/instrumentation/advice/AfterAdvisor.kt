package com.dickow.chortlin.interception.instrumentation.advice

import com.dickow.chortlin.interception.configuration.InterceptionStrategy
import com.dickow.chortlin.shared.observation.Observation
import com.dickow.chortlin.shared.trace.Return
import net.bytebuddy.asm.Advice
import net.bytebuddy.implementation.bytecode.assign.Assigner
import java.lang.reflect.Method

@Suppress("UNUSED_PARAMETER")
object AfterAdvisor {
    @JvmStatic
    @Advice.OnMethodExit
    fun afterMethod(
            @Advice.AllArguments allArguments: Array<Any?>,
            @Advice.Origin method: Method,
            @Advice.Return(typing = Assigner.Typing.DYNAMIC) returnValue: Any?) {
        InterceptionStrategy.strategy.intercept(Return(Observation(method.declaringClass, method), allArguments, returnValue))
    }
}