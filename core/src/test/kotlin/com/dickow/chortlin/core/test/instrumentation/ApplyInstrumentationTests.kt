package com.dickow.chortlin.core.test.instrumentation

import com.dickow.chortlin.core.checker.result.CheckResult
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.external
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
    private val interceptStrategy: InterceptStrategy = object : InterceptStrategy {

        override fun intercept(trace: TraceElement) {
            traces.add(trace)
        }
    }

    private val external = external("external client")

    // First set of participants
    private val initial = participant(Initial::class.java, "begin")
    private val delegate = participant(Initial::class.java, "delegate")
    private val processor = participant(Second::class.java, "process")

    // Second set of participants
    private val firstClass = participant(FirstClass::class.java, "first")
    private val secondClass = participant(SecondClass::class.java, "second")
    private val thirdClass = participant(ThirdClass::class.java, "third")

    // Third set of participants
    private val partialFirst1 = participant(PartialFirst::class.java, "first")
    private val partialFirst2 = participant(PartialFirst::class.java, "second")
    private val partialSecond2 = participant(PartialSecond::class.java, "second")
    private val partialSecond3 = participant(PartialSecond::class.java, "third")
    private val partialThird3 = participant(PartialThird::class.java, "third")

    @Test
    fun `apply instrumentation to simple in memory communication`() {
        traces.clear()
        InstrumentationStrategy.strategy = interceptStrategy
        val checker = Choreography.builder()
                .interaction(external, initial, "start")
                .interaction(initial.nonObservable, delegate, "delegate")
                .interaction(delegate.nonObservable, processor, "processing")
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
        InstrumentationStrategy.strategy = interceptStrategy
        val checker = Choreography.builder()
                .interaction(external, delegate, "start")
                .interaction(delegate.nonObservable, initial, "then initial")
                .interaction(initial.nonObservable, processor, "process it")
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
        InstrumentationStrategy.strategy = interceptStrategy
        val checker = Choreography.builder()
                .interaction(external, firstClass, "initial receive")
                .interaction(firstClass.nonObservable, secondClass, "second call")
                .interaction(secondClass.nonObservable, thirdClass, "third call")
                .returnFrom(thirdClass, "return from third call")
                .returnFrom(secondClass, "return from Second::second")
                .returnFrom(firstClass, "return from First::first")
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
        InstrumentationStrategy.strategy = interceptStrategy
        val checker = Choreography.builder()
                .interaction(external, firstClass, "initial receive")
                .interaction(firstClass.nonObservable, secondClass, "second call")
                .interaction(secondClass.nonObservable, thirdClass, "third call")
                .returnFrom(thirdClass, "return from third call")
                .returnFrom(secondClass, "return from Second::second")
                .returnFrom(firstClass, "return from First::first")
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
        InstrumentationStrategy.strategy = interceptStrategy
        val checker = Choreography.builder()
                .interaction(external, partialFirst1, "initialize calls")
                .interaction(partialFirst1.nonObservable, partialFirst2, "call second method of first class")
                .interaction(partialFirst2.nonObservable, partialSecond2, "call second method of second class")
                .interaction(partialSecond2.nonObservable, partialSecond3, "call third method of second class")
                .interaction(partialSecond3.nonObservable, partialThird3, "call third method of third class")
                .returnFrom(partialThird3, "return from the third participant again")
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