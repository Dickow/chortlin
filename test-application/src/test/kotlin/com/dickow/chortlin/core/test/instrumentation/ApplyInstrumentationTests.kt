package com.dickow.chortlin.core.test.instrumentation

import com.dickow.chortlin.checker.checker.factory.CheckerFactory
import com.dickow.chortlin.checker.checker.result.CheckResult
import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.external
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.checker.correlation.factory.CorrelationFactory.correlation
import com.dickow.chortlin.checker.correlation.factory.CorrelationFactory.defineCorrelation
import com.dickow.chortlin.core.test.shared.*
import com.dickow.chortlin.interception.instrumentation.ByteBuddyInstrumentation
import com.dickow.chortlin.interception.instrumentation.strategy.InstrumentationStrategy
import com.dickow.chortlin.interception.instrumentation.strategy.InterceptStrategy
import com.dickow.chortlin.shared.trace.Trace
import com.dickow.chortlin.shared.trace.TraceElement
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplyInstrumentationTests {
    val traces: MutableList<TraceElement> = LinkedList()
    private val interceptStrategy: InterceptStrategy = object : InterceptStrategy {

        override fun intercept(trace: TraceElement) {
            traces.add(trace)
        }
    }

    init {
        ByteBuddyInstrumentation.instrumentAnnotatedMethods()
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
        val cset = defineCorrelation()
                .add(correlation(initial.onMethod("begin", Initial::begin), "sid", { sessionId })
                        .extendFromInput("sid") { sessionId }
                        .done())
                .add(correlation(delegate.onMethod("delegate", Initial::delegate), "sid", { sessionId }).noExtensions())
                .add(correlation(processor.onMethod("process", Second::process), "sid", { sessionId }).noExtensions())
                .finish()

        val checker = CheckerFactory.createChecker(
                Choreography.builder()
                        .interaction(external, initial.onMethod("begin"), "start")
                        .interaction(initial, delegate.onMethod("delegate"), "delegate")
                        .interaction(delegate, processor.onMethod("process"), "processing")
                        .end().setCorrelation(cset))
        Initial().begin()
        assertEquals(3, traces.size)
        assertEquals(CheckResult.Full, checker.check(Trace(traces)))
    }

    @Test
    fun `validate that instrumentation catches an error in the invocation`() {
        traces.clear()
        InstrumentationStrategy.strategy = interceptStrategy
        val sessionId = UUID.randomUUID()
        val cset = defineCorrelation()
                .add(correlation(delegate.onMethod("delegate", Initial::delegate), "sid", { sessionId }).extendFromInput("sid") { sessionId }.done())
                .add(correlation(initial.onMethod("begin", Initial::begin), "sid", { sessionId }).noExtensions())
                .add(correlation(processor.onMethod("process", Second::process), "sid", { sessionId }).noExtensions())
                .finish()

        val checker = CheckerFactory.createChecker(
                Choreography.builder()
                        .interaction(external, delegate.onMethod("delegate"), "start")
                        .interaction(delegate, initial.onMethod("begin"), "then initial")
                        .interaction(initial, processor.onMethod("process"), "process it")
                        .end().setCorrelation(cset)
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
        val cset = defineCorrelation()
                .add(correlation(firstClass.onMethod("first", FirstClass::first), "sid", { sessionId }).extendFromInput("sid") { sessionId }.done())
                .add(correlation(secondClass.onMethod("second", SecondClass::second), "sid", { sessionId }).noExtensions())
                .add(correlation(thirdClass.onMethod("third", ThirdClass::third), "sid", { sessionId }).noExtensions())
                .finish()

        val checker = CheckerFactory.createChecker(
                Choreography.builder()
                        .interaction(external, firstClass.onMethod("first"), "initial receive")
                        .interaction(firstClass, secondClass.onMethod("second"), "second call")
                        .interaction(secondClass, thirdClass.onMethod("third"), "third call")
                        .returnFrom(thirdClass.onMethod("third"), "return from third call")
                        .returnFrom(secondClass.onMethod("second"), "return from Second::second")
                        .returnFrom(firstClass.onMethod("first"), "return from First::first")
                        .end().setCorrelation(cset)
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
        val cset = defineCorrelation()
                .add(correlation(firstClass.onMethod("first", FirstClass::first), "sid", { sessionId }).extendFromInput("sid") { sessionId }.done())
                .add(correlation(secondClass.onMethod("second", SecondClass::second), "sid", { sessionId }).noExtensions())
                .add(correlation(thirdClass.onMethod("third", ThirdClass::third), "sid", { sessionId }).noExtensions())
                .finish()

        val checker = CheckerFactory.createChecker(
                Choreography.builder()
                        .interaction(external, firstClass.onMethod("first"), "initial receive")
                        .interaction(firstClass, secondClass.onMethod("second"), "second call")
                        .interaction(secondClass, thirdClass.onMethod("third"), "third call")
                        .returnFrom(thirdClass.onMethod("third"), "return from third call")
                        .returnFrom(secondClass.onMethod("second"), "return from Second::second")
                        .returnFrom(firstClass.onMethod("first"), "return from First::first")
                        .end().setCorrelation(cset)
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
        val cset = defineCorrelation()
                .add(correlation(partialFirst1.onMethod("first", PartialFirst::first), "sid", { sessionId }).extendFromInput("sid") { sessionId }.done())
                .add(correlation(partialFirst1.onMethod("second", PartialFirst::second), "sid", { sessionId }).noExtensions())
                .add(correlation(partialSecond2.onMethod("second", PartialSecond::second), "sid", { sessionId }).noExtensions())
                .add(correlation(partialSecond2.onMethod("third", PartialSecond::third), "sid", { sessionId }).noExtensions())
                .add(correlation(partialThird3.onMethod("third", PartialThird::third), "sid", { sessionId }).noExtensions())
                .finish()

        val checker = CheckerFactory.createChecker(
                Choreography.builder()
                        .interaction(external, partialFirst1.onMethod("first"), "initialize calls")
                        .interaction(partialFirst1, partialFirst1.onMethod("second"), "call second method of first class")
                        .interaction(partialFirst2, partialSecond2.onMethod("second"), "call second method of second class")
                        .interaction(partialSecond2, partialSecond2.onMethod("third"), "call third method of second class")
                        .interaction(partialSecond3, partialThird3.onMethod("third"), "call third method of third class")
                        .returnFrom(partialThird3.onMethod("third"), "return from the third participant again")
                        .end().setCorrelation(cset)
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