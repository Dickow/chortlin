package com.dickow.chortlin.bytebuddytest

import net.bytebuddy.asm.Advice
import java.lang.reflect.Method

class MethodAdvisor {
    companion object {
        @JvmStatic
        @Advice.OnMethodEnter
        fun beforeMethod(@Advice.AllArguments allArguments: Array<Any>, @Advice.Origin method: Method) {
            println("Called the before method of the Method advisor")
        }
    }

}