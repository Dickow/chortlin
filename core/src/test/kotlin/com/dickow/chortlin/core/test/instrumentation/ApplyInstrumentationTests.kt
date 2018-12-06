package com.dickow.chortlin.core.test.instrumentation

import com.dickow.chortlin.core.checker.factory.CheckerFactory
import com.dickow.chortlin.core.checker.result.CheckResult
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.external
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.correlation.factory.CorrelationFactory.correlation
import com.dickow.chortlin.core.correlation.factory.CorrelationFactory.defineCorrelationSet
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
    private val initial = participant(Initial::class.java) // "begin", Initial::begin
    private val delegate = participant(Initial::class.java) // "delegate", Initial::delegate
    private val processor = participant(Second::class.java) // "process", Second::process

    // Second set of participants
    private val firstClass = participant(FirstClass::class.java) // "first", FirstClass::first
    private val secondClass = participant(SecondClass::class.java) // "second", SecondClass::second
    private val thirdClass = participant(ThirdClass::class.java) // "third", ThirdClass::third

    // Third set of participants
    private val partialFirst1 = participant(PartialFirst::class.java) // "first", PartialFirst::first
    private val partialFirst2 = participant(PartialFirst::class.java) // "second", PartialFirst::second
    private val partialSecond2 = participant(PartialSecond::class.java) // "second", PartialSecond::second
    private val partialSecond3 = participant(PartialSecond::class.java) // "third", PartialSecond::third
    private val partialThird3 = participant(PartialThird::class.java) // "third", PartialThird::third

    @Test
    fun `apply instrumentation to simple in memory communication`() {
        traces.clear()
        InstrumentationStrategy.strategy = interceptStrategy
        val sessionId = UUID.randomUUID()
        val cset = defineCorrelationSet()
                .add(correlation(initial.onMethod("begin", Initial::begin), { sessionId })
                        .extendFromInput { sessionId }
                        .done())
                .add(correlation(delegate.onMethod("delegate", Initial::delegate), { sessionId }).noExtensions())
                .add(correlation(processor.onMethod("process", Second::process), { sessionId }).noExtensions())
                .finish()

        val checker = CheckerFactory.createChecker(
                Choreography.builder()
                        .interaction(external, initial.onMethod("begin", Initial::begin), "start")
                        .interaction(initial, delegate.onMethod("delegate", Initial::delegate), "delegate")
                        .interaction(delegate, processor.onMethod("process", Second::process), "processing")
                        .end().setCorrelationSet(cset)
                        .runVisitor(instrumentationVisitor))
        Initial().begin()
        assertEquals(3, traces.size)
        assertEquals(CheckResult.Full, checker.check(Trace(traces)))
    }

    @Test
    fun `validate that instrumentation catches an error in the invocation`() {
        traces.clear()
        InstrumentationStrategy.strategy = interceptStrategy
        val sessionId = UUID.randomUUID()
        val cset = defineCorrelationSet()
                .add(correlation(delegate.onMethod("delegate", Initial::delegate), { sessionId }).extendFromInput { sessionId }.done())
                .add(correlation(initial.onMethod("begin", Initial::begin), { sessionId }).noExtensions())
                .add(correlation(processor.onMethod("process", Second::process), { sessionId }).noExtensions())
                .finish()

        val checker = CheckerFactory.createChecker(
                Choreography.builder()
                        .interaction(external, delegate.onMethod("delegate", Initial::delegate), "start")
                        .interaction(delegate, initial.onMethod("begin", Initial::begin), "then initial")
                        .interaction(initial, processor.onMethod("process", Second::process), "process it")
                        .end().setCorrelationSet(cset)
                        .runVisitor(instrumentationVisitor)
        )
        Initial().begin()
        assertEquals(3, traces.size)
        assertEquals(CheckResult.None, checker.check(Trace(traces)))
    }

    @Test
    fun `validate instrumentation when returns are used correctly`() {
        traces.clear()
        InstrumentationStrategy.strategy = interceptStrategy
        val sessionId = UUID.randomUUID()
        val cset = defineCorrelationSet()
                .add(correlation(firstClass.onMethod("first", FirstClass::first), { sessionId }).extendFromInput { sessionId }.done())
                .add(correlation(secondClass.onMethod("second", SecondClass::second), { sessionId }).noExtensions())
                .add(correlation(thirdClass.onMethod("third", ThirdClass::third), { sessionId }).noExtensions())
                .finish()

        val checker = CheckerFactory.createChecker(
                Choreography.builder()
                        .interaction(external, firstClass.onMethod("first", FirstClass::first), "initial receive")
                        .interaction(firstClass, secondClass.onMethod("second", SecondClass::second), "second call")
                        .interaction(secondClass, thirdClass.onMethod("third", ThirdClass::third), "third call")
                        .returnFrom(thirdClass.onMethod("third", ThirdClass::third), "return from third call")
                        .returnFrom(secondClass.onMethod("second", SecondClass::second), "return from Second::second")
                        .returnFrom(firstClass.onMethod("first", FirstClass::first), "return from First::first")
                        .end().setCorrelationSet(cset)
                        .runVisitor(instrumentationVisitor)
        )

        FirstClass().first()
        assertEquals(6, traces.size)
        assertEquals(CheckResult.Full, checker.check(Trace(traces)))
    }

    @Test
    fun `check that checker invalidates gathered traces for wrong call sequence`() {
        traces.clear()
        InstrumentationStrategy.strategy = interceptStrategy
        val sessionId = UUID.randomUUID()
        val cset = defineCorrelationSet()
                .add(correlation(firstClass.onMethod("first", FirstClass::first), { sessionId }).extendFromInput { sessionId }.done())
                .add(correlation(secondClass.onMethod("second", SecondClass::second), { sessionId }).noExtensions())
                .add(correlation(thirdClass.onMethod("third", ThirdClass::third), { sessionId }).noExtensions())
                .finish()

        val checker = CheckerFactory.createChecker(
                Choreography.builder()
                        .interaction(external, firstClass.onMethod("first", FirstClass::first), "initial receive")
                        .interaction(firstClass, secondClass.onMethod("second", SecondClass::second), "second call")
                        .interaction(secondClass, thirdClass.onMethod("third", ThirdClass::third), "third call")
                        .returnFrom(thirdClass.onMethod("third", ThirdClass::third), "return from third call")
                        .returnFrom(secondClass.onMethod("second", SecondClass::second), "return from Second::second")
                        .returnFrom(firstClass.onMethod("first", FirstClass::first), "return from First::first")
                        .end().setCorrelationSet(cset)
                        .runVisitor(instrumentationVisitor)
        )

        SecondClass().second()
        assertEquals(4, traces.size)
        assertEquals(CheckResult.None, checker.check(Trace(traces)))
    }

    @Test
    fun `check that traces gathered from instrumentation partially matches when partially executed`() {
        traces.clear()
        InstrumentationStrategy.strategy = interceptStrategy
        val sessionId = UUID.randomUUID()
        val cset = defineCorrelationSet()
                .add(correlation(partialFirst1.onMethod("first", PartialFirst::first), { sessionId }).extendFromInput { sessionId }.done())
                .add(correlation(partialFirst1.onMethod("second", PartialFirst::second), { sessionId }).noExtensions())
                .add(correlation(partialSecond2.onMethod("second", PartialSecond::second), { sessionId }).noExtensions())
                .add(correlation(partialSecond2.onMethod("third", PartialSecond::third), { sessionId }).noExtensions())
                .add(correlation(partialThird3.onMethod("third", PartialThird::third), { sessionId }).noExtensions())
                .finish()

        val checker = CheckerFactory.createChecker(
                Choreography.builder()
                        .interaction(external, partialFirst1.onMethod("first", PartialFirst::first), "initialize calls")
                        .interaction(partialFirst1, partialFirst1.onMethod("second", PartialFirst::second), "call second method of first class")
                        .interaction(partialFirst2, partialSecond2.onMethod("second", PartialSecond::second), "call second method of second class")
                        .interaction(partialSecond2, partialSecond2.onMethod("third", PartialSecond::third), "call third method of second class")
                        .interaction(partialSecond3, partialThird3.onMethod("third", PartialThird::third), "call third method of third class")
                        .returnFrom(partialThird3.onMethod("third", PartialThird::third), "return from the third participant again")
                        .end().setCorrelationSet(cset)
                        .runVisitor(instrumentationVisitor)
        )

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