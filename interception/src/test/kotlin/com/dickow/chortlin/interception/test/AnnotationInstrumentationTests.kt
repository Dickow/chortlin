package com.dickow.chortlin.interception.test

import com.dickow.chortlin.interception.InterceptStrategy
import com.dickow.chortlin.interception.configuration.InterceptionStrategy
import com.dickow.chortlin.interception.instrumentation.ByteBuddyInstrumentation
import com.dickow.chortlin.interception.observation.Observation
import com.dickow.chortlin.interception.sending.dto.TraceDTOFactory
import com.dickow.chortlin.shared.trace.protobuf.DtoDefinitions
import com.google.protobuf.Value
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AnnotationInstrumentationTests {

    private val builder = TraceDTOFactory()
    private val traces = LinkedList<Any>()

    init {
        ByteBuddyInstrumentation.instrumentAnnotatedMethods()
    }

    @BeforeEach
    internal fun setUp() {
        traces.clear()
        InterceptionStrategy.strategy = object : InterceptStrategy {
            override fun interceptInvocation(observable: Observation, arguments: Array<out Any?>) {
                val trace = builder.buildInvocationDTO(observable, arguments)
                traces.add(trace)
            }

            override fun interceptReturn(observable: Observation, arguments: Array<out Any?>, returnValue: Any?) {
                val trace = builder.buildReturnDTO(observable, arguments, returnValue)
                traces.add(trace)
            }
        }
    }

    @Test
    fun `check that annotated methods are intercepted correctly`(){
        Login().authenticate("jeppe", "password")
        MerchantService().buyItem(100, "veryUnique")
        assertEquals(4, traces.size)
    }

    @Test
    fun `check that method with void return types are intercepted on return as well`(){
        Login().authenticate("jeppe", "password")
        MerchantService().sellItem(100, 9001)
        assertEquals(4, traces.size)
        assertTrue { traces[3] is DtoDefinitions.ReturnDTO }
        val returnDTO = (traces[3] as DtoDefinitions.ReturnDTO)
        assertTrue(returnDTO.hasReturnValue() && returnDTO.returnValue.kindCase == Value.KindCase.NULL_VALUE)
    }

    @Test
    fun `check that methods that are not annotated are not intercepted`(){
        Login().authenticate("jeppe", "password")
        AnonymousService().unknownMethod("do not intercept me")
        assertEquals(2, traces.size)
    }
}