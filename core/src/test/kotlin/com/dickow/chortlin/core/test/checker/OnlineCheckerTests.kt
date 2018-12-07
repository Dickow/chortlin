package com.dickow.chortlin.core.test.checker

import com.dickow.chortlin.core.checker.OnlineChecker
import com.dickow.chortlin.core.checker.result.CheckResult
import com.dickow.chortlin.core.checker.session.InMemorySessionManager
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.external
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.choreography.participant.observation.ObservableFactory.observed
import com.dickow.chortlin.core.correlation.factory.CorrelationFactory.correlation
import com.dickow.chortlin.core.correlation.factory.CorrelationFactory.defineCorrelation
import com.dickow.chortlin.core.test.shared.OnlineFirstClass
import com.dickow.chortlin.core.test.shared.OnlineSecondClass
import com.dickow.chortlin.core.test.shared.OnlineThirdClass
import com.dickow.chortlin.core.trace.Invocation
import com.dickow.chortlin.core.trace.Return
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class OnlineCheckerTests {
    private val external = external("External client")
    private val onlineFirst = participant(OnlineFirstClass::class.java)
    private val onlineSecond = participant(OnlineSecondClass::class.java)
    private val onlineThird = participant(OnlineThirdClass::class.java)

    private val sessionId = UUID.randomUUID()
    private val cdef = defineCorrelation()
            .add(correlation(onlineFirst.onMethod("method1", OnlineFirstClass::method1)) { sessionId }.extendFromInput { sessionId }.done())
            .add(correlation(onlineFirst.onMethod("method2", OnlineFirstClass::method2)) { sessionId }.noExtensions())
            .add(correlation(onlineSecond.onMethod("method1", OnlineSecondClass::method1)) { sessionId }.noExtensions())
            .add(correlation(onlineSecond.onMethod("method2", OnlineSecondClass::method2)) { sessionId }.noExtensions())
            .add(correlation(onlineThird.onMethod("method1", OnlineThirdClass::method1)) { sessionId }.noExtensions())
            .add(correlation(onlineThird.onMethod("method2", OnlineThirdClass::method2)) { sessionId }.noExtensions())
            .finish()

    private val allArguments = arrayOf<Any>()
    private val returnValue = Any()

    private val choreography = Choreography.builder()
            .interaction(external, onlineFirst.onMethod("method1", OnlineFirstClass::method1), "#1")
            .interaction(onlineFirst, onlineFirst.onMethod("method2", OnlineFirstClass::method2), "#2")
            .interaction(onlineFirst, onlineSecond.onMethod("method1", OnlineSecondClass::method1), "#3")
            .interaction(onlineSecond, onlineSecond.onMethod("method2", OnlineSecondClass::method2), "#4")
            .interaction(onlineSecond, onlineThird.onMethod("method1", OnlineThirdClass::method1), "#5")
            .interaction(onlineThird, onlineThird.onMethod("method2", OnlineThirdClass::method2), "#6")
            .returnFrom(onlineThird.onMethod("method2", OnlineThirdClass::method2), "return #6")
            .returnFrom(onlineThird.onMethod("method1", OnlineThirdClass::method1), "return #5")
            .returnFrom(onlineSecond.onMethod("method2", OnlineSecondClass::method2), "return #4")
            .returnFrom(onlineSecond.onMethod("method1", OnlineSecondClass::method1), "return #3")
            .returnFrom(onlineFirst.onMethod("method2", OnlineFirstClass::method2), "return #2")
            .returnFrom(onlineFirst.onMethod("method1", OnlineFirstClass::method1), "return #1")
            .end()
            .setCorrelationSet(cdef)

    private val expectedTraceSequence = listOf(
            Invocation(observed(OnlineFirstClass::class.java, "method1"), allArguments),
            Invocation(observed(OnlineFirstClass::class.java, "method2"), allArguments),
            Invocation(observed(OnlineSecondClass::class.java, "method1"), allArguments),
            Invocation(observed(OnlineSecondClass::class.java, "method2"), allArguments),
            Invocation(observed(OnlineThirdClass::class.java, "method1"), allArguments),
            Invocation(observed(OnlineThirdClass::class.java, "method2"), allArguments),
            Return(observed(OnlineThirdClass::class.java, "method2"), allArguments, returnValue),
            Return(observed(OnlineThirdClass::class.java, "method1"), allArguments, returnValue),
            Return(observed(OnlineSecondClass::class.java, "method2"), allArguments, returnValue),
            Return(observed(OnlineSecondClass::class.java, "method1"), allArguments, returnValue),
            Return(observed(OnlineFirstClass::class.java, "method2"), allArguments, returnValue),
            Return(observed(OnlineFirstClass::class.java, "method1"), allArguments, returnValue)
    )

    @Test
    fun `check correctness of online checker when simulating execution`() {
        val onlineChecker = OnlineChecker(InMemorySessionManager(listOf(choreography)))
        val allExceptLastTrace = expectedTraceSequence.subList(0, expectedTraceSequence.size - 1)
        allExceptLastTrace.forEach { trace -> assertEquals(CheckResult.Partial, onlineChecker.check(trace)) }
        assertEquals(CheckResult.Full, onlineChecker.check(expectedTraceSequence.last()))
    }

    @Test
    fun `ensure that out of order execution is rejected`() {
        val onlineChecker = OnlineChecker(InMemorySessionManager(listOf(choreography)))
        assertEquals(CheckResult.Partial, onlineChecker.check(Invocation(observed(OnlineFirstClass::class.java, "method1"), allArguments)))
        assertEquals(CheckResult.Partial, onlineChecker.check(Invocation(observed(OnlineFirstClass::class.java, "method2"), allArguments)))
        assertEquals(CheckResult.Partial, onlineChecker.check(Invocation(observed(OnlineSecondClass::class.java, "method1"), allArguments)))

        // Apply the out of order execution
        assertEquals(CheckResult.None, onlineChecker.check(Invocation(observed(OnlineThirdClass::class.java, "method2"), allArguments)))
    }

    @Test
    fun `check that checker can start over once a session finished successfully`() {
        val onlineChecker = OnlineChecker(InMemorySessionManager(listOf(choreography)))
        expectedTraceSequence.forEach { traceElement -> onlineChecker.check(traceElement) }

        val allExceptLastTrace = expectedTraceSequence.subList(0, expectedTraceSequence.size - 1)
        allExceptLastTrace.forEach { trace -> assertEquals(CheckResult.Partial, onlineChecker.check(trace)) }
        assertEquals(CheckResult.Full, onlineChecker.check(expectedTraceSequence.last()))
    }

    @Test
    fun `check that a new instance can start when one has failed`() {
        val onlineChecker = OnlineChecker(InMemorySessionManager(listOf(choreography)))
        onlineChecker.check(Invocation(observed(OnlineFirstClass::class.java, "method1"), allArguments))
        onlineChecker.check(Invocation(observed(OnlineFirstClass::class.java, "method2"), allArguments))
        onlineChecker.check(Invocation(observed(OnlineSecondClass::class.java, "method1"), allArguments))

        // Apply the out of order execution
        onlineChecker.check(Invocation(observed(OnlineThirdClass::class.java, "method2"), allArguments))

        // Then check that a new instance can run
        val allExceptLastTrace = expectedTraceSequence.subList(0, expectedTraceSequence.size - 1)
        allExceptLastTrace.forEach { trace -> assertEquals(CheckResult.Partial, onlineChecker.check(trace)) }
        assertEquals(CheckResult.Full, onlineChecker.check(expectedTraceSequence.last()))
    }

    @Test
    fun `check that online checker works for unrelated choreographies with interleaved execution`() {
        val sessionId1 = UUID.randomUUID()
        val sessionId2 = UUID.randomUUID()
        val cset1 = defineCorrelation()
                .add(correlation(onlineFirst.onMethod("method1", OnlineFirstClass::method1)) { sessionId1 }.extendFromInput { sessionId1 }.done())
                .add(correlation(onlineFirst.onMethod("method2", OnlineFirstClass::method2)) { sessionId1 }.noExtensions())
                .add(correlation(onlineSecond.onMethod("method1", OnlineSecondClass::method1)) { sessionId1 }.noExtensions())
                .finish()
        val cset2 = defineCorrelation()
                .add(correlation(onlineSecond.onMethod("method2", OnlineSecondClass::method2)) { sessionId2 }.extendFromInput { sessionId2 }.done())
                .add(correlation(onlineThird.onMethod("method1", OnlineThirdClass::method1)) { sessionId2 }.noExtensions())
                .add(correlation(onlineThird.onMethod("method2", OnlineThirdClass::method2)) { sessionId2 }.noExtensions())
                .finish()

        val choreography1 = Choreography.builder()
                .interaction(external, onlineFirst.onMethod("method1"), "#1")
                .interaction(onlineFirst, onlineFirst.onMethod("method2"), "#2")
                .interaction(onlineFirst, onlineSecond.onMethod("method1"), "#3")
                .returnFrom(onlineSecond.onMethod("method1"), "return #3")
                .end().setCorrelationSet(cset1)

        val choreography2 = Choreography.builder()
                .interaction(external, onlineSecond.onMethod("method2"), "#1")
                .interaction(onlineSecond, onlineThird.onMethod("method1"), "#2")
                .interaction(onlineThird, onlineThird.onMethod("method2"), "#3")
                .end().setCorrelationSet(cset2)
        val checker = OnlineChecker(InMemorySessionManager(listOf(choreography1, choreography2)))
        assertEquals(CheckResult.Partial, checker.check(Invocation(observed(OnlineFirstClass::class.java, "method1"), allArguments))) // Choreography 1
        assertEquals(CheckResult.Partial, checker.check(Invocation(observed(OnlineSecondClass::class.java, "method2"), allArguments))) // Choreography 2
        assertEquals(CheckResult.Partial, checker.check(Invocation(observed(OnlineFirstClass::class.java, "method2"), allArguments))) // Choreography 1
        assertEquals(CheckResult.Partial, checker.check(Invocation(observed(OnlineSecondClass::class.java, "method1"), allArguments))) // Choreography 1
        assertEquals(CheckResult.Partial, checker.check(Invocation(observed(OnlineThirdClass::class.java, "method1"), allArguments))) // Choreography 2
        assertEquals(CheckResult.Full, checker.check(Return(observed(OnlineSecondClass::class.java, "method1"), allArguments, returnValue))) // Choreography 1
        assertEquals(CheckResult.Full, checker.check(Invocation(observed(OnlineThirdClass::class.java, "method2"), allArguments))) // Choreography 2
    }
}