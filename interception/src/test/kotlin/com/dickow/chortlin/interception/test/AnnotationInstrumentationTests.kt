package com.dickow.chortlin.interception.test

import com.dickow.chortlin.interception.InterceptStrategy
import com.dickow.chortlin.interception.configuration.InterceptionStrategy
import com.dickow.chortlin.interception.instrumentation.ByteBuddyInstrumentation
import com.dickow.chortlin.shared.trace.TraceElement
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class AnnotationInstrumentationTests {

    init {
        ByteBuddyInstrumentation.instrumentAnnotatedMethods()
    }


    @Test
    fun `check that annotated methods are intercepted correctly`(){
        val traces = LinkedList<TraceElement>()
        InterceptionStrategy.strategy = object : InterceptStrategy {
            override fun intercept(trace: TraceElement) {
                traces.add(trace)
            }
        }

        Login().authenticate("jeppe", "password")
        MerchantService().buyItem(100, "veryUnique")
        assertEquals(4, traces.size)
    }

    @Test
    fun `check that method with void return types are intercepted on return as well`(){
        val traces = LinkedList<TraceElement>()
        InterceptionStrategy.strategy = object : InterceptStrategy {
            override fun intercept(trace: TraceElement) {
                traces.add(trace)
            }
        }

        Login().authenticate("jeppe", "password")
        MerchantService().sellItem(100, 9001)
        assertEquals(4, traces.size)
    }

    @Test
    fun `check that methods that are not annotated are not intercepted`(){
        val traces = LinkedList<TraceElement>()
        InterceptionStrategy.strategy = object : InterceptStrategy {
            override fun intercept(trace: TraceElement) {
                traces.add(trace)
            }
        }

        Login().authenticate("jeppe", "password")
        AnonymousService().unknownMethod("do not intercept me")
        assertEquals(2, traces.size)
    }
}