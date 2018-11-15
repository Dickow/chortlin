package com.dickow.chortlin.core.test.checker

import com.dickow.chortlin.core.checker.OnlineChecker
import com.dickow.chortlin.core.checker.result.CheckResult
import com.dickow.chortlin.core.checker.session.InMemorySessionManager
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.test.shared.OnlineFirstClass
import com.dickow.chortlin.core.test.shared.OnlineSecondClass
import com.dickow.chortlin.core.test.shared.OnlineThirdClass
import com.dickow.chortlin.core.trace.Invocation
import com.dickow.chortlin.core.trace.Return
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class OnlineCheckerTests {

    private val choreography = Choreography.builder()
            .foundMessage(participant(OnlineFirstClass::class.java, "method1"), "#1")
            .interaction(participant(OnlineFirstClass::class.java, "method2"),
                    participant(OnlineSecondClass::class.java, "method1"),
                    "#2")
            .interaction(participant(OnlineSecondClass::class.java, "method2"),
                    participant(OnlineThirdClass::class.java, "method1"),
                    "#3")
            .foundMessage(participant(OnlineThirdClass::class.java, "method2"), "#4")
            .returnFrom(participant(OnlineThirdClass::class.java, "method2"), "return #4")
            .returnFrom(participant(OnlineThirdClass::class.java, "method1"), "return #3(2)")
            .returnFrom(participant(OnlineSecondClass::class.java, "method2"), "return #3(1)")
            .returnFrom(participant(OnlineSecondClass::class.java, "method1"), "return #2(2)")
            .returnFrom(participant(OnlineFirstClass::class.java, "method2"), "return #2(1)")
            .returnFrom(participant(OnlineFirstClass::class.java, "method1"), "return #1")
            .end()

    private val expectedTraceSequence = listOf(
            Invocation(participant(OnlineFirstClass::class.java, "method1")),
            Invocation(participant(OnlineFirstClass::class.java, "method2")),
            Invocation(participant(OnlineSecondClass::class.java, "method1")),
            Invocation(participant(OnlineSecondClass::class.java, "method2")),
            Invocation(participant(OnlineThirdClass::class.java, "method1")),
            Invocation(participant(OnlineThirdClass::class.java, "method2")),
            Return(participant(OnlineThirdClass::class.java, "method2")),
            Return(participant(OnlineThirdClass::class.java, "method1")),
            Return(participant(OnlineSecondClass::class.java, "method2")),
            Return(participant(OnlineSecondClass::class.java, "method1")),
            Return(participant(OnlineFirstClass::class.java, "method2")),
            Return(participant(OnlineFirstClass::class.java, "method1"))
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
        assertEquals(CheckResult.Partial, onlineChecker.check(Invocation(participant(OnlineFirstClass::class.java, "method1"))))
        assertEquals(CheckResult.Partial, onlineChecker.check(Invocation(participant(OnlineFirstClass::class.java, "method2"))))
        assertEquals(CheckResult.Partial, onlineChecker.check(Invocation(participant(OnlineSecondClass::class.java, "method1"))))

        // Apply the out of order execution
        assertEquals(CheckResult.None, onlineChecker.check(Invocation(participant(OnlineThirdClass::class.java, "method2"))))
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
        onlineChecker.check(Invocation(participant(OnlineFirstClass::class.java, "method1")))
        onlineChecker.check(Invocation(participant(OnlineFirstClass::class.java, "method2")))
        onlineChecker.check(Invocation(participant(OnlineSecondClass::class.java, "method1")))

        // Apply the out of order execution
        onlineChecker.check(Invocation(participant(OnlineThirdClass::class.java, "method2")))

        // Then check that a new instance can run
        val allExceptLastTrace = expectedTraceSequence.subList(0, expectedTraceSequence.size - 1)
        allExceptLastTrace.forEach { trace -> assertEquals(CheckResult.Partial, onlineChecker.check(trace)) }
        assertEquals(CheckResult.Full, onlineChecker.check(expectedTraceSequence.last()))
    }

    @Test
    fun `check that online checker works for unrelated choreographies with interleaved execution`() {
        val choreography1 = Choreography.builder()
                .foundMessage(participant(OnlineFirstClass::class.java, "method1"), "#1")
                .interaction(participant(OnlineFirstClass::class.java, "method2"),
                        participant(OnlineSecondClass::class.java, "method1"),
                        "#2")
                .returnFrom(participant(OnlineSecondClass::class.java, "method1"), "return #2")
                .end()

        val choreography2 = Choreography.builder()
                .foundMessage(participant(OnlineSecondClass::class.java, "method2"), "#1")
                .interaction(participant(OnlineThirdClass::class.java, "method1"),
                        participant(OnlineThirdClass::class.java, "method2"),
                        "#2")
                .end()
        val checker = OnlineChecker(InMemorySessionManager(listOf(choreography1, choreography2)))
        assertEquals(CheckResult.Partial, checker.check(Invocation(participant(OnlineFirstClass::class.java, "method1")))) // Choreography 1
        assertEquals(CheckResult.Partial, checker.check(Invocation(participant(OnlineSecondClass::class.java, "method2")))) // Choreography 2
        assertEquals(CheckResult.Partial, checker.check(Invocation(participant(OnlineFirstClass::class.java, "method2")))) // Choreography 1
        assertEquals(CheckResult.Partial, checker.check(Invocation(participant(OnlineSecondClass::class.java, "method1")))) // Choreography 1
        assertEquals(CheckResult.Partial, checker.check(Invocation(participant(OnlineThirdClass::class.java, "method1")))) // Choreography 2
        assertEquals(CheckResult.Full, checker.check(Return(participant(OnlineSecondClass::class.java, "method1")))) // Choreography 1
        assertEquals(CheckResult.Full, checker.check(Invocation(participant(OnlineThirdClass::class.java, "method2")))) // Choreography 2
    }
}