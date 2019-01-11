package com.dickow.chortlin.checker.test

import com.dickow.chortlin.checker.choreography.participant.ObservableParticipant
import com.dickow.chortlin.checker.trace.Invocation
import com.dickow.chortlin.checker.trace.Return
import com.dickow.chortlin.checker.trace.TraceEvent
import com.dickow.chortlin.checker.trace.value.RootValue
import com.dickow.chortlin.checker.trace.value.StringValue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class TraceEqualityTests {

    @Test
    fun `check equality of trace classes`() {
        val p_m = ObservableParticipant("p", "m")
        val strInputValue = RootValue(StringValue("HELLO"))
        val invocation1 = Invocation(p_m, strInputValue)
        val invocation2 = Invocation(p_m, strInputValue)
        val return1 = Return(p_m, strInputValue, strInputValue)
        val return2 = Return(p_m, strInputValue, strInputValue)

        assertEquals(invocation1, invocation2)
        assertNotEquals<TraceEvent>(invocation1, return1)
        assertEquals(return1, return2)
        assertEquals(return1.hashCode(), return2.hashCode())
        assertEquals(invocation1.hashCode(), invocation2.hashCode())
    }
}