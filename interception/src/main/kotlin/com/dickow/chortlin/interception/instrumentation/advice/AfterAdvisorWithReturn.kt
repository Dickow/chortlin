package com.dickow.chortlin.interception.instrumentation.advice

import com.dickow.chortlin.interception.strategy.InstrumentationStrategy
import com.dickow.chortlin.shared.observation.Observation
import com.dickow.chortlin.shared.trace.Return
import net.bytebuddy.asm.Advice
import net.bytebuddy.implementation.bytecode.assign.Assigner
import java.lang.reflect.Method

@Suppress("UNUSED_PARAMETER")
class AfterAdvisorWithReturn {
    companion object {
        @JvmStatic
        @Advice.OnMethodExit
        fun afterMethodWithReturnType(
                @Advice.AllArguments allArguments: Array<Any?>,
                @Advice.Origin method: Method,
                @Advice.Return(typing = Assigner.Typing.DYNAMIC) returnValue: Any?)
        {
            InstrumentationStrategy.strategy.intercept(Return(Observation(method.declaringClass, method), allArguments, returnValue))
        }
    }
}