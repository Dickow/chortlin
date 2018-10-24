package com.dickow.chortlin.core.instrumentation.advice

import net.bytebuddy.asm.Advice
import java.lang.reflect.Method

class AfterAdvisor {
    companion object {
        @JvmStatic
        @Advice.OnMethodExit
        fun afterMethod(@Advice.AllArguments allArguments: Array<Any>, @Advice.Origin method: Method) {
            println("After invocation of ${method.name}")
        }
    }
}