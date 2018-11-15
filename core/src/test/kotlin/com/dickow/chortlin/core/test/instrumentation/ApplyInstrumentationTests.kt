package com.dickow.chortlin.core.test.instrumentation

import com.dickow.chortlin.core.checker.result.CheckResult
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.instrumentation.ASTInstrumentation
import com.dickow.chortlin.core.instrumentation.ByteBuddyInstrumentation
import com.dickow.chortlin.core.instrumentation.strategy.InstrumentationStrategy
import com.dickow.chortlin.core.instrumentation.strategy.InterceptStrategy
import com.dickow.chortlin.core.test.shared.*
import com.dickow.chortlin.core.trace.Trace
import com.dickow.chortlin.core.trace.TraceElement
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplyInstrumentationTests {
    private val instrumentationVisitor = ASTInstrumentation(ByteBuddyInstrumentation)
    val traces: MutableList<TraceElement> = LinkedList()
    private val InterceptStrategy: InterceptStrategy = object : InterceptStrategy {

        override fun intercept(trace: TraceElement) {
            traces.add(trace)
        }
    }

    @Test
    fun `apply instrumentation to simple in memory communication`() {
        traces.clear()
        InstrumentationStrategy.strategy = InterceptStrategy
        val checker = Choreography.builder()
                .foundMessage(participant(Initial::class.java, "begin"), "start")
                .interaction(participant(Initial::class.java, "delegate"),
                        participant(Second::class.java, "process"), "interaction")
                .end()
                .runVisitor(instrumentationVisitor)
                .createChecker()
        Initial().begin()
        assertEquals(3, traces.size)
        assertEquals(CheckResult.Full, checker.check(Trace(traces)))
    }

    @Test
    fun `validate that instrumentation catches an error in the invocation`() {
        traces.clear()
        InstrumentationStrategy.strategy = InterceptStrategy
        val checker = Choreography.builder()
                .foundMessage(participant(Initial::class.java, "delegate"), "start")
                .interaction(participant(Initial::class.java, "begin"),
                        participant(Second::class.java, "process"), "interaction")
                .end()
                .runVisitor(instrumentationVisitor)
                .createChecker()
        Initial().begin()
        assertEquals(3, traces.size)
        assertEquals(CheckResult.None, checker.check(Trace(traces)))
    }

    @Test
    fun `validate instrumentation when returns are used correctly`() {
        traces.clear()
        InstrumentationStrategy.strategy = InterceptStrategy
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
        assertEquals(CheckResult.Full, checker.check(Trace(traces)))
    }

    @Test
    fun `check that checker invalidates gathered traces for wrong call sequence`() {
        traces.clear()
        InstrumentationStrategy.strategy = InterceptStrategy
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
        assertEquals(CheckResult.None, checker.check(Trace(traces)))
    }

    @Test
    fun `check that traces gathered from instrumentation partially matches when partially executed`() {
        traces.clear()
        InstrumentationStrategy.strategy = InterceptStrategy
        val checker = Choreography.builder()
                .foundMessage(participant(PartialFirst::class.java, "first"), "initialize calls")
                .interaction(participant(PartialFirst::class.java, "second"),
                        participant(PartialSecond::class.java, "second"), "call second class from first class")
                .interaction(participant(PartialSecond::class.java, "third"),
                        participant(PartialThird::class.java, "third"), "call third class from second class")
                .returnFrom(participant(PartialThird::class.java, "third"), "return from the third participant again")
                .end()
                .runVisitor(instrumentationVisitor)
                .createChecker()

        PartialFirst().first()
        assertEquals(3, traces.size)
        assertEquals(CheckResult.Partial, checker.check(Trace(traces)))

        // Now simulate the remaining invocations manually
        PartialSecond().third()
        PartialThird().third()
        assertEquals(6, traces.size)
        assertEquals(CheckResult.Full, checker.check(Trace(traces)))
    }
}