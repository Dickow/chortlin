package com.dickow.chortlin.core.test.checker

import com.dickow.chortlin.core.checker.result.CheckResult
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.external
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
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
    private val aReceive = participant(A::class.java, "receive")
    private val aB = participant(A::class.java, "b")
    private val bB = participant(B::class.java, "b")

    @Test
    fun `check that trace conforms to configured choreography`() {
        val choreography = Choreography.builder()
                .interaction(external, aReceive, "receive")
                .end()
        val trace = Trace(listOf(Invocation(participant(A::class.java, "receive"))))
        val checker = choreography.createChecker()
        assertEquals(CheckResult.Full, checker.check(trace))
        assertEquals(CheckResult.Full, checker.check(trace))
    }

    @Test
    fun `ensure that single end is invalid as everything matches a single end`() {
        assertFailsWith(InvalidASTException::class) { Choreography.builder().end() }
    }

    @Test
    fun `check that full choreography matches the example trace`() {
        val choreography = Choreography.builder()
                .interaction(external, aReceive, "receive")
                .interaction(aReceive.nonObservable, aB, "call A#b")
                .interaction(aB.nonObservable, bB, "invoke B#b")
                .end()
        val trace = Trace(listOf(
                Invocation(participant(A::class.java, "receive")),
                Invocation(participant(A::class.java, "b")),
                Invocation(participant(B::class.java, "b"))))

        val checker = choreography.createChecker()
        assertEquals(CheckResult.Full, checker.check(trace))
        assertEquals(CheckResult.Full, checker.check(trace))
    }

    @Test
    fun `check that trace in wrong order is not accepted`() {
        val choreography = Choreography.builder()
                .interaction(external, aReceive, "receive")
                .interaction(aReceive.nonObservable, aB, "call A#b")
                .interaction(aB.nonObservable, bB, "invoke B#b")
                .end()
        val trace = Trace(listOf(
                Invocation(participant(A::class.java, "b")),
                Invocation(participant(A::class.java, "receive")),
                Invocation(participant(B::class.java, "b"))))

        val checker = choreography.createChecker()
        assertEquals(CheckResult.None, checker.check(trace))
        assertEquals(CheckResult.None, checker.check(trace))
    }

    @Test
    fun `check that return is validated for traces`() {
        val choreography = Choreography.builder()
                .interaction(external, aReceive, "receive")
                .interaction(aReceive.nonObservable, aB, "call A#b")
                .interaction(aB.nonObservable, bB, "invoke B#b")
                .returnFrom(bB, "return from method B::b")
                .returnFrom(aB, "return from method A::b")
                .end()
        val checker = choreography.createChecker()
        val trace = Trace(listOf(
                Invocation(participant(A::class.java, "receive")),
                Invocation(participant(A::class.java, "b")),
                Invocation(participant(B::class.java, "b")),
                Return(participant(B::class.java, "b")),
                Return(participant(A::class.java, "b"))))
        assertEquals(CheckResult.Full, checker.check(trace))
    }

    @Test
    fun `check that found message with return trace is accepted`() {
        val choreography = Choreography.builder()
                .interaction(external, aReceive, "receive")
                .returnFrom(aReceive, "return")
                .end()
        val checker = choreography.createChecker()
        val trace = Trace(listOf(
                Invocation(participant(A::class.java, "receive")),
                Return(participant(A::class.java, "receive"))))
        assertEquals(CheckResult.Full, checker.check(trace))
    }

    @Test
    fun `check that trace is only partially valid when return trace is missing`() {
        val choreography = Choreography.builder()
                .interaction(external, aReceive, "receive")
                .returnFrom(aReceive, "return")
                .end()
        val checker = choreography.createChecker()
        val trace = Trace(listOf(Invocation(participant(A::class.java, "receive"))))
        assertEquals(CheckResult.Partial, checker.check(trace))
    }

    @Test
    fun `check that trace is not valid when return from wrong participant is encountered`() {
        val choreography = Choreography.builder()
                .interaction(external, aReceive, "receive")
                .returnFrom(aReceive, "return")
                .end()
        val checker = choreography.createChecker()
        val trace = Trace(listOf(
                Invocation(participant(A::class.java, "receive")),
                Return(participant(B::class.java, "b"))))
        assertEquals(CheckResult.None, checker.check(trace))
    }

}