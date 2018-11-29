package com.dickow.chortlin.core.test.checker

import com.dickow.chortlin.core.checker.OnlineChecker
import com.dickow.chortlin.core.checker.result.CheckResult
import com.dickow.chortlin.core.checker.session.InMemorySessionManager
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.external
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.correlation.factory.CorrelationFactory.addFunctions
import com.dickow.chortlin.core.correlation.factory.CorrelationFactory.correlation
import com.dickow.chortlin.core.correlation.factory.CorrelationFactory.defineCorrelationSet
import com.dickow.chortlin.core.correlation.factory.CorrelationFactory.fromInput
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
    private val onlineFirstMethod1 = participant(OnlineFirstClass::class.java, "method1")
    private val onlineFirstMethod2 = participant(OnlineFirstClass::class.java, "method2")
    private val onlineSecondMethod1 = participant(OnlineSecondClass::class.java, "method1")
    private val onlineSecondMethod2 = participant(OnlineSecondClass::class.java, "method2")
    private val onlineThirdMethod1 = participant(OnlineThirdClass::class.java, "method1")
    private val onlineThirdMethod2 = participant(OnlineThirdClass::class.java, "method2")

    private val sessionId = UUID.randomUUID()
    private val cset = defineCorrelationSet()
            .add(correlation(onlineFirstMethod1, { sessionId }, addFunctions(fromInput { sessionId })))
            .add(correlation(onlineFirstMethod2, { sessionId }))
            .add(correlation(onlineSecondMethod1, { sessionId }))
            .add(correlation(onlineSecondMethod2, { sessionId }))
            .add(correlation(onlineThirdMethod1, { sessionId }))
            .add(correlation(onlineThirdMethod2, { sessionId }))
            .finish()

    private val allArguments = arrayOf<Any>()
    private val returnValue = Any()

    private val choreography = Choreography.builder()
            .interaction(external, onlineFirstMethod1, "#1")
            .interaction(onlineFirstMethod1.nonObservable, onlineFirstMethod2, "#2")
            .interaction(onlineFirstMethod2.nonObservable, onlineSecondMethod1, "#3")
            .interaction(onlineSecondMethod1.nonObservable, onlineSecondMethod2, "#4")
            .interaction(onlineSecondMethod2.nonObservable, onlineThirdMethod1, "#5")
            .interaction(onlineThirdMethod1.nonObservable, onlineThirdMethod2, "#6")
            .returnFrom(onlineThirdMethod2, "return #6")
            .returnFrom(onlineThirdMethod1, "return #5")
            .returnFrom(onlineSecondMethod2, "return #4")
            .returnFrom(onlineSecondMethod1, "return #3")
            .returnFrom(onlineFirstMethod2, "return #2")
            .returnFrom(onlineFirstMethod1, "return #1")
            .end()
            .setCorrelationSet(cset)

    private val expectedTraceSequence = listOf(
            Invocation(participant(OnlineFirstClass::class.java, "method1"), allArguments),
            Invocation(participant(OnlineFirstClass::class.java, "method2"), allArguments),
            Invocation(participant(OnlineSecondClass::class.java, "method1"), allArguments),
            Invocation(participant(OnlineSecondClass::class.java, "method2"), allArguments),
            Invocation(participant(OnlineThirdClass::class.java, "method1"), allArguments),
            Invocation(participant(OnlineThirdClass::class.java, "method2"), allArguments),
            Return(participant(OnlineThirdClass::class.java, "method2"), allArguments, returnValue),
            Return(participant(OnlineThirdClass::class.java, "method1"), allArguments, returnValue),
            Return(participant(OnlineSecondClass::class.java, "method2"), allArguments, returnValue),
            Return(participant(OnlineSecondClass::class.java, "method1"), allArguments, returnValue),
            Return(participant(OnlineFirstClass::class.java, "method2"), allArguments, returnValue),
            Return(participant(OnlineFirstClass::class.java, "method1"), allArguments, returnValue)
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
        assertEquals(CheckResult.Partial, onlineChecker.check(Invocation(participant(OnlineFirstClass::class.java, "method1"), allArguments)))
        assertEquals(CheckResult.Partial, onlineChecker.check(Invocation(participant(OnlineFirstClass::class.java, "method2"), allArguments)))
        assertEquals(CheckResult.Partial, onlineChecker.check(Invocation(participant(OnlineSecondClass::class.java, "method1"), allArguments)))

        // Apply the out of order execution
        assertEquals(CheckResult.None, onlineChecker.check(Invocation(participant(OnlineThirdClass::class.java, "method2"), allArguments)))
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
        onlineChecker.check(Invocation(participant(OnlineFirstClass::class.java, "method1"), allArguments))
        onlineChecker.check(Invocation(participant(OnlineFirstClass::class.java, "method2"), allArguments))
        onlineChecker.check(Invocation(participant(OnlineSecondClass::class.java, "method1"), allArguments))

        // Apply the out of order execution
        onlineChecker.check(Invocation(participant(OnlineThirdClass::class.java, "method2"), allArguments))

        // Then check that a new instance can run
        val allExceptLastTrace = expectedTraceSequence.subList(0, expectedTraceSequence.size - 1)
        allExceptLastTrace.forEach { trace -> assertEquals(CheckResult.Partial, onlineChecker.check(trace)) }
        assertEquals(CheckResult.Full, onlineChecker.check(expectedTraceSequence.last()))
    }

    @Test
    fun `check that online checker works for unrelated choreographies with interleaved execution`() {
        val sessionId1 = UUID.randomUUID()
        val sessionId2 = UUID.randomUUID()
        val cset1 = defineCorrelationSet()
                .add(correlation(onlineFirstMethod1, { sessionId1 }, addFunctions(fromInput { sessionId1 })))
                .add(correlation(onlineFirstMethod2, { sessionId1 }))
                .add(correlation(onlineSecondMethod1, { sessionId1 }))
                .finish()
        val cset2 = defineCorrelationSet()
                .add(correlation(onlineSecondMethod2, { sessionId2 }, addFunctions(fromInput { sessionId2 })))
                .add(correlation(onlineThirdMethod1, { sessionId2 }))
                .add(correlation(onlineThirdMethod2, { sessionId2 }))
                .finish()

        val choreography1 = Choreography.builder()
                .interaction(external, onlineFirstMethod1, "#1")
                .interaction(onlineFirstMethod1.nonObservable, onlineFirstMethod2, "#2")
                .interaction(onlineFirstMethod2.nonObservable, onlineSecondMethod1, "#3")
                .returnFrom(onlineSecondMethod1, "return #3")
                .end().setCorrelationSet(cset1)

        val choreography2 = Choreography.builder()
                .interaction(external, onlineSecondMethod2, "#1")
                .interaction(onlineSecondMethod2.nonObservable, onlineThirdMethod1, "#2")
                .interaction(onlineThirdMethod1.nonObservable, onlineThirdMethod2, "#3")
                .end().setCorrelationSet(cset2)
        val checker = OnlineChecker(InMemorySessionManager(listOf(choreography1, choreography2)))
        assertEquals(CheckResult.Partial, checker.check(Invocation(participant(OnlineFirstClass::class.java, "method1"), allArguments))) // Choreography 1
        assertEquals(CheckResult.Partial, checker.check(Invocation(participant(OnlineSecondClass::class.java, "method2"), allArguments))) // Choreography 2
        assertEquals(CheckResult.Partial, checker.check(Invocation(participant(OnlineFirstClass::class.java, "method2"), allArguments))) // Choreography 1
        assertEquals(CheckResult.Partial, checker.check(Invocation(participant(OnlineSecondClass::class.java, "method1"), allArguments))) // Choreography 1
        assertEquals(CheckResult.Partial, checker.check(Invocation(participant(OnlineThirdClass::class.java, "method1"), allArguments))) // Choreography 2
        assertEquals(CheckResult.Full, checker.check(Return(participant(OnlineSecondClass::class.java, "method1"), allArguments, returnValue))) // Choreography 1
        assertEquals(CheckResult.Full, checker.check(Invocation(participant(OnlineThirdClass::class.java, "method2"), allArguments))) // Choreography 2
    }
}