package com.dickow.chortlin.bytebuddytest

import net.bytebuddy.agent.builder.AgentBuilder
import net.bytebuddy.asm.Advice
import net.bytebuddy.matcher.ElementMatchers


fun main(args: Array<String>) {
    testStuff()
}

fun testStuff() {
    //
    AgentBuilder.Default()
            .disableClassFormatChanges()
            .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
            .type(ElementMatchers.named(Intercepted::class.java.name))
            .transform { builder, typeDescription, classLoader, javaModule ->
                builder.visit(Advice.to(MethodAdvisor::class.java).on(ElementMatchers.named("doSomeMagic")))
            }
            .installOnByteBuddyAgent()
    val methodName = MethodUtil.getMethodName(Interception2::doSomething)
    AgentBuilder.Default()
            .disableClassFormatChanges()
            .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
            .type(ElementMatchers.named(Interception2::class.java.name))
            .transform { builder, typeDescription, classLoader, javaModule ->
                builder.visit(Advice.to(AroundAdvisor::class.java).on(ElementMatchers.named(methodName)))
            }
            .installOnByteBuddyAgent()

    Intercepted().doSomeMagic("Hello", 42)
    Interception2().doSomething(arrayOf("Hello", "world", "!"))
}

class Intercepted {
    fun doSomeMagic(arg1: String, arg2: Int): Array<Any> {
        println("doing something")
        return arrayOf(arg1, arg2)
    }
}

open class Interception2 {
    fun doSomething(arg1: Array<String>?): String? {
        arg1!!.forEach { println(it) }
        return arg1.joinToString { s -> s }
    }
}