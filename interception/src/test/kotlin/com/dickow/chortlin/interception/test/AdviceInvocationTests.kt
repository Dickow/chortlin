package com.dickow.chortlin.interception.test

import com.dickow.chortlin.interception.InterceptStrategy
import com.dickow.chortlin.interception.configuration.InterceptionStrategy
import com.dickow.chortlin.interception.configuration.defaults.DoNothingStrategy
import com.dickow.chortlin.interception.instrumentation.advice.AfterAdvisor
import com.dickow.chortlin.interception.instrumentation.advice.BeforeAdvisor
import com.dickow.chortlin.interception.observation.Observation
import kotlin.test.Test
import kotlin.test.assertEquals

class AdviceInvocationTests {

    private val customStrategy = CustomTestInterceptStrategy({ _, _ -> }, { _, _, _ -> })

    @Test
    fun `call before advisor with custom interception and default`() {
        val method = this.javaClass.getMethod("call before advisor with custom interception and default")
        InterceptionStrategy.strategy = DoNothingStrategy()
        BeforeAdvisor.beforeMethod(arrayOf("hello", 19, true), method)
        customStrategy.invocationCallBack = { obs, args ->
            assertEquals("hello", args[0])
            assertEquals(19, args[1])
            assertEquals(true, args[2])
            assertEquals(AdviceInvocationTests::class.java, obs.clazz)
            assertEquals(method, obs.jvmMethod)
        }
        InterceptionStrategy.strategy = customStrategy
        BeforeAdvisor.beforeMethod(arrayOf("hello", 19, true), method)
    }

    @Test
    fun `call after advisor with custom interception and default`() {
        val method = this.javaClass.getMethod("call after advisor with custom interception and default")
        val arguments = arrayOf<Any?>("hello", 42, true)
        InterceptionStrategy.strategy = DoNothingStrategy()
        AfterAdvisor.afterMethod(arguments, method, "RETURN_VALUE")
        customStrategy.returnCallBack = { obs, args, retVal ->
            assertEquals(arguments, args)
            assertEquals(AdviceInvocationTests::class.java, obs.clazz)
            assertEquals(method, obs.jvmMethod)
            assertEquals("RETURN_VALUE", retVal)
        }
        InterceptionStrategy.strategy = customStrategy
        AfterAdvisor.afterMethod(arguments, method, "RETURN_VALUE")
    }
}

class CustomTestInterceptStrategy(
        var invocationCallBack: (Observation, Array<out Any?>) -> Unit,
        var returnCallBack: (Observation, Array<out Any?>, Any?) -> Unit) : InterceptStrategy {
    override fun interceptInvocation(observation: Observation, arguments: Array<out Any?>) {
        invocationCallBack(observation, arguments)
    }

    override fun interceptReturn(observation: Observation, arguments: Array<out Any?>, returnValue: Any?) {
        returnCallBack(observation, arguments, returnValue)
    }
}