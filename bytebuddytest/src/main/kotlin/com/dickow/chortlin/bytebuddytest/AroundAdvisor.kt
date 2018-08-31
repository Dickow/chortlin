package com.dickow.chortlin.bytebuddytest

import net.bytebuddy.asm.Advice
import java.lang.reflect.Method

class AroundAdvisor {
    companion object {
        @JvmStatic
        @Advice.OnMethodEnter
        fun beforeMethod(@Advice.AllArguments allArguments: Array<Any>, @Advice.Origin method: Method) {
            throw RuntimeException("yes my man this is for realzz")
            println("Called the before method of the Method advisor")
        }

        @JvmStatic
        fun afterMethod(@Advice.AllArguments allArguments: Array<Any>, @Advice.Origin method: Method) {
            println("Called the after method of the Method advisor")
        }
    }
}