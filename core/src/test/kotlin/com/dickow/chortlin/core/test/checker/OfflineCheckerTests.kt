package com.dickow.chortlin.core.test.checker

import com.dickow.chortlin.core.checker.ChoreographyChecker
import com.dickow.chortlin.core.checker.result.CheckResult
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.external
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.choreography.participant.observation.ObservableFactory.observed
import com.dickow.chortlin.core.exceptions.InvalidASTException
import com.dickow.chortlin.core.test.shared.A
import com.dickow.chortlin.core.test.shared.B
import com.dickow.chortlin.core.trace.Invocation
import com.dickow.chortlin.core.trace.Return
import com.dickow.chortlin.core.trace.Trace
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class OfflineCheckerTests {

    private val external = external("external")
    private val a = participant(A::class.java) // "receive", A::receive
    private val b = participant(B::class.java) // "b", B::b
    private val allArguments = arrayOf<Any>()
    private val returnValue = Any()

    @Test
    fun `check that trace conforms to configured choreography`() {
        val choreography = Choreography.builder()
                .interaction(external, a.onMethod("receive"), "receive")
                .end()
        val trace = Trace(listOf(Invocation(observed(A::class.java, "receive"), allArguments)))
        val checker = ChoreographyChecker(choreography)
        assertEquals(CheckResult.Full, checker.check(trace))
        assertEquals(CheckResult.Full, checker.check(trace))
    }

    @kotlin.test.Test
    fun `ensure that single end is invalid as everything matches a single end`() {
        assertFailsWith(InvalidASTException::class) { Choreography.builder().end() }
    }

    @Test
    fun `check that full choreography matches the example trace`() {
        val choreography = Choreography.builder()
                .interaction(external, a.onMethod("receive"), "receive")
                .interaction(a, a.onMethod("b"), "call A#b")
                .interaction(a, b.onMethod("b"), "invoke B#b")
                .end()
        val trace = Trace(listOf(
                Invocation(observed(A::class.java, "receive"), allArguments),
                Invocation(observed(A::class.java, "b"), allArguments),
                Invocation(observed(B::class.java, "b"), allArguments)))

        val checker = ChoreographyChecker(choreography)
        assertEquals(CheckResult.Full, checker.check(trace))
        assertEquals(CheckResult.Full, checker.check(trace))
    }

    @Test
    fun `check that trace in wrong order is not accepted`() {
        val choreography = Choreography.builder()
                .interaction(external, a.onMethod("receive"), "receive")
                .interaction(a, a.onMethod("b"), "call A#b")
                .interaction(a, b.onMethod("b"), "invoke B#b")
                .end()
        val trace = Trace(listOf(
                Invocation(observed(A::class.java, "b"), allArguments),
                Invocation(observed(A::class.java, "receive"), allArguments),
                Invocation(observed(B::class.java, "b"), allArguments)))

        val checker = ChoreographyChecker(choreography)
        assertEquals(CheckResult.None, checker.check(trace))
        assertEquals(CheckResult.None, checker.check(trace))
    }

    @Test
    fun `check that return is validated for traces`() {
        val choreography = Choreography.builder()
                .interaction(external, a.onMethod("receive"), "receive")
                .interaction(a, a.onMethod("b"), "call A#b")
                .interaction(a, b.onMethod("b"), "invoke B#b")
                .returnFrom(b.onMethod("b"), "return from method B::b")
                .returnFrom(a.onMethod("b"), "return from method A::b")
                .end()
        val checker = ChoreographyChecker(choreography)
        val trace = Trace(listOf(
                Invocation(observed(A::class.java, "receive"), allArguments),
                Invocation(observed(A::class.java, "b"), allArguments),
                Invocation(observed(B::class.java, "b"), allArguments),
                Return(observed(B::class.java, "b"), allArguments, returnValue),
                Return(observed(A::class.java, "b"), allArguments, returnValue)))
        assertEquals(CheckResult.Full, checker.check(trace))
    }

    @Test
    fun `check that found message with return trace is accepted`() {
        val choreography = Choreography.builder()
                .interaction(external, a.onMethod("receive"), "receive")
                .returnFrom(a.onMethod("receive"), "return")
                .end()
        val checker = ChoreographyChecker(choreography)
        val trace = Trace(listOf(
                Invocation(observed(A::class.java, "receive"), allArguments),
                Return(observed(A::class.java, "receive"), allArguments, returnValue)))
        assertEquals(CheckResult.Full, checker.check(trace))
    }

    @Test
    fun `check that trace is only partially valid when return trace is missing`() {
        val choreography = Choreography.builder()
                .interaction(external, a.onMethod("receive"), "receive")
                .returnFrom(a.onMethod("receive"), "return")
                .end()
        val checker = ChoreographyChecker(choreography)
        val trace = Trace(listOf(Invocation(observed(A::class.java, "receive"), allArguments)))
        assertEquals(CheckResult.Partial, checker.check(trace))
    }

    @Test
    fun `check that trace is not valid when return from wrong participant is encountered`() {
        val choreography = Choreography.builder()
                .interaction(external, a.onMethod("receive"), "receive")
                .returnFrom(a.onMethod("receive"), "return")
                .end()
        val checker = ChoreographyChecker(choreography)
        val trace = Trace(listOf(
                Invocation(observed(A::class.java, "receive"), allArguments),
                Return(observed(B::class.java, "b"), allArguments, returnValue)))
        assertEquals(CheckResult.None, checker.check(trace))
    }

}