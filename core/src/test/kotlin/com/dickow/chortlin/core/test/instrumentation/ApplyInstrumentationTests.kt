package com.dickow.chortlin.core.test.instrumentation

import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.instrumentation.ASTInstrumentation
import com.dickow.chortlin.core.instrumentation.ByteBuddyInstrumentation
import com.dickow.chortlin.core.instrumentation.strategy.InstrumentationStrategy
import com.dickow.chortlin.core.instrumentation.strategy.StorageStrategy
import com.dickow.chortlin.core.test.shared.*
import com.dickow.chortlin.core.trace.Trace
import com.dickow.chortlin.core.trace.TraceElement
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ApplyInstrumentationTests {
    private val instrumentationVisitor = ASTInstrumentation(ByteBuddyInstrumentation)
    val traces: MutableList<TraceElement> = LinkedList()
    private val storageStrategy: StorageStrategy = object : StorageStrategy {

        override fun store(trace: TraceElement) {
            traces.add(trace)
        }
    }

    @Test
    fun `apply instrumentation to simple in memory communication`() {
        traces.clear()
        InstrumentationStrategy.strategy = storageStrategy
        val checker = Choreography.builder()
                .foundMessage(participant(Initial::class.java, "begin"), "start")
                .interaction(participant(Initial::class.java, "delegate"),
                        participant(Second::class.java, "process"), "interaction")
                .end()
                .runVisitor(instrumentationVisitor)
                .createChecker()
        Initial().begin()
        assertEquals(3, traces.size)
        assertTrue(checker.check(Trace(traces.toTypedArray())))
    }


    @Test
    fun `validate that instrumentation catches an error in the invocation`() {
        traces.clear()
        InstrumentationStrategy.strategy = storageStrategy
        val checker = Choreography.builder()
                .foundMessage(participant(Initial::class.java, "delegate"), "start")
                .interaction(participant(Initial::class.java, "begin"),
                        participant(Second::class.java, "process"), "interaction")
                .end()
                .runVisitor(instrumentationVisitor)
                .createChecker()
        Initial().begin()
        assertEquals(3, traces.size)
        assertFalse(checker.check(Trace(traces.toTypedArray())))
    }

    @Test
    fun `validate instrumentation when returns are used correctly`() {
        traces.clear()
        InstrumentationStrategy.strategy = storageStrategy
        val checker = Choreography.builder()
                .interaction(participant(FirstClass::class.java, "first"),
                        participant(SecondClass::class.java, "second"), "initial call")
                .foundMessage(participant(ThirdClass::class.java, "third"), "last call")
                .returnFrom(participant(ThirdClass::class.java, "third"), "return from third call")
                .returnFrom(participant(SecondClass::class.java, "second"), "return from Second::second")
                .returnFrom(participant(FirstClass::class.java, "first"), "First::first")
                .end()
                .runVisitor(instrumentationVisitor)
                .createChecker()

        FirstClass().first()
        assertEquals(6, traces.size)
        assertTrue(checker.check(Trace(traces.toTypedArray())))
    }

    @Test
    fun `check that checker invalidates gathered traces for wrong call sequence`() {
        traces.clear()
        InstrumentationStrategy.strategy = storageStrategy
        val checker = Choreography.builder()
                .interaction(participant(FirstClass::class.java, "first"),
                        participant(SecondClass::class.java, "second"), "initial call")
                .foundMessage(participant(ThirdClass::class.java, "third"), "last call")
                .returnFrom(participant(ThirdClass::class.java, "third"), "return from third call")
                .returnFrom(participant(SecondClass::class.java, "second"), "return from Second::second")
                .returnFrom(participant(FirstClass::class.java, "first"), "First::first")
                .end()
                .runVisitor(instrumentationVisitor)
                .createChecker()

        SecondClass().second()
        assertEquals(4, traces.size)
        assertFalse(checker.check(Trace(traces.toTypedArray())))
    }
}