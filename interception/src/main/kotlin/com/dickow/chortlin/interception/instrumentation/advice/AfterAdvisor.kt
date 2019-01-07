package com.dickow.chortlin.interception.instrumentation.advice

import com.dickow.chortlin.interception.configuration.InterceptionStrategy
import com.dickow.chortlin.interception.observation.Observation
import net.bytebuddy.asm.Advice
import net.bytebuddy.implementation.bytecode.assign.Assigner
import java.lang.reflect.Method

object AfterAdvisor {
    @JvmStatic
    @Advice.OnMethodExit
    fun afterMethod(
            @Advice.AllArguments allArguments: Array<Any?>,
            @Advice.Origin method: Method,
            @Advice.Return(typing = Assigner.Typing.DYNAMIC) returnValue: Any?) {
        InterceptionStrategy.strategy.interceptReturn(Observation(method.declaringClass, method), allArguments, returnValue)
    }
}