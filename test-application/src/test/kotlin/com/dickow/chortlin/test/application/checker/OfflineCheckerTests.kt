package com.dickow.chortlin.test.application.checker

import com.dickow.chortlin.checker.ast.types.factory.TypeFactory.choice
import com.dickow.chortlin.checker.ast.types.factory.TypeFactory.interaction
import com.dickow.chortlin.checker.checker.result.CheckResult
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.external
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.checker.trace.Trace
import com.dickow.chortlin.test.application.shared.A
import com.dickow.chortlin.test.application.shared.B
import com.dickow.chortlin.test.application.shared.builder.TestObservableBuilder.buildInvocation
import com.dickow.chortlin.test.application.shared.builder.TestObservableBuilder.buildReturn
import kotlin.test.Test
import kotlin.test.assertEquals

class OfflineCheckerTests {

    private val external = external("external")
    private val a = participant(A::class.java) // "receive", A::receive
    private val b = participant(B::class.java) // "b", B::b
    private val allArguments = arrayOf<Any?>()
    private val returnValue = Any()

    @Test
    fun `check that trace conforms to configured choreography`() {
        val choreography =
                interaction(external, a.onMethod("receive"), "receive")
                .end()
        val trace = listOf(buildInvocation(A::class.java, "receive", allArguments))
        assertEquals(CheckResult.Full, choreography.start.satisfy(Trace(trace)))
        assertEquals(CheckResult.Full, choreography.start.satisfy(Trace(trace)))
    }

    @Test
    fun `check that full choreography matches the example trace`() {
        val choreography =
                interaction(external, a.onMethod("receive"), "receive")
                .interaction(a, a.onMethod("b"), "call A#b")
                .interaction(a, b.onMethod("b"), "invoke B#b")
                .end()
        val traces = listOf(
                buildInvocation(A::class.java, "receive", allArguments),
                buildInvocation(A::class.java, "b", allArguments),
                buildInvocation(B::class.java, "b", allArguments))

        assertEquals(CheckResult.Full, choreography.start.satisfy(Trace(traces)))
        assertEquals(CheckResult.Full, choreography.start.satisfy(Trace(traces)))
    }

    @Test
    fun `check that trace in wrong order is not accepted`() {
        val choreography =
                interaction(external, a.onMethod("receive"), "receive")
                .interaction(a, a.onMethod("b"), "call A#b")
                .interaction(a, b.onMethod("b"), "invoke B#b")
                .end()

        val traces = listOf(
                buildInvocation(A::class.java, "b", allArguments),
                buildInvocation(A::class.java, "receive", allArguments),
                buildInvocation(B::class.java, "b", allArguments))

        assertEquals(CheckResult.None, choreography.start.satisfy(Trace(traces)))
        assertEquals(CheckResult.None, choreography.start.satisfy(Trace(traces)))
    }

    @Test
    fun `check that return is validated for traces`() {
        val choreography =
                interaction(external, a.onMethod("receive"), "receive")
                .interaction(a, a.onMethod("b"), "call A#b")
                .interaction(a, b.onMethod("b"), "invoke B#b")
                .returnFrom(b.onMethod("b"), "return from method B::b")
                .returnFrom(a.onMethod("b"), "return from method A::b")
                .end()

        val traces = listOf(
                buildInvocation(A::class.java, "receive", allArguments),
                buildInvocation(A::class.java, "b", allArguments),
                buildInvocation(B::class.java, "b", allArguments),
                buildReturn(B::class.java, "b", allArguments, returnValue),
                buildReturn(A::class.java, "b", allArguments, returnValue))
        assertEquals(CheckResult.Full, choreography.start.satisfy(Trace(traces)))
    }

    @Test
    fun `check that interaction followed by return is accepted`() {
        val choreography =
                interaction(external, a.onMethod("receive"), "receive")
                .returnFrom(a.onMethod("receive"), "return")
                .end()
        val traces = listOf(
                buildInvocation(A::class.java, "receive", allArguments),
                buildReturn(A::class.java, "receive", allArguments, returnValue))
        assertEquals(CheckResult.Full, choreography.start.satisfy(Trace(traces)))
    }

    @Test
    fun `check that trace is only partially valid when return trace is missing`() {
        val choreography =
                interaction(external, a.onMethod("receive"), "receive")
                .returnFrom(a.onMethod("receive"), "return")
                .end()
        val traces = listOf(buildInvocation(A::class.java, "receive", allArguments))
        assertEquals(CheckResult.Partial, choreography.start.satisfy(Trace(traces)))
    }

    @Test
    fun `check that trace is not valid when return from wrong participant is encountered`() {
        val choreography =
                interaction(external, a.onMethod("receive"), "receive")
                .returnFrom(a.onMethod("receive"), "return")
                .end()
        val traces = listOf(
                buildInvocation(A::class.java, "receive", allArguments),
                buildReturn(B::class.java, "b", allArguments, returnValue))
        assertEquals(CheckResult.None, choreography.start.satisfy(Trace(traces)))
    }

    @Test
    fun `check that beginning choice satisfies correct trace`() {
        val choreography = choice(
                interaction(external, a.onMethod("receive"), "receive")
                        .returnFrom(a.onMethod("receive"), "return")
                        .end(),
                interaction(external, b.onMethod("b"), "b receive")
                        .end()
        )
        val traces1 = listOf(
                buildInvocation(A::class.java, "receive", allArguments),
                buildReturn(A::class.java, "receive", allArguments, returnValue))
        val traces2 = listOf(
                buildInvocation(B::class.java, "b", allArguments))
        assertEquals(CheckResult.Full, choreography.start.satisfy(Trace(traces1)))
        assertEquals(CheckResult.Full, choreography.start.satisfy(Trace(traces2)))
    }

    @Test
    fun `check that beginning choice invalidates wrong trace`() {
        val choreography = choice(
                interaction(external, a.onMethod("receive"), "receive")
                        .returnFrom(a.onMethod("receive"), "return")
                        .end(),
                interaction(external, b.onMethod("b"), "b receive")
                        .end()
        )
        val tracesInWrongOrder = listOf(
                buildReturn(A::class.java, "receive", allArguments, returnValue),
                buildInvocation(A::class.java, "receive", allArguments))
        assertEquals(CheckResult.None, choreography.start.satisfy(Trace(tracesInWrongOrder)))
    }

    @Test
    fun `check that choice with duplicate branches still matches correct trace`() {
        val choreography = choice(
                interaction(external, a.onMethod("receive"), "receive")
                        .returnFrom(a.onMethod("receive"), "return")
                        .end(),
                interaction(external, a.onMethod("receive"), "receive")
                        .returnFrom(a.onMethod("receive"), "return")
                        .end()
        )
        val traces = listOf(
                buildInvocation(A::class.java, "receive", allArguments),
                buildReturn(A::class.java, "receive", allArguments, returnValue))
        assertEquals(CheckResult.Full, choreography.start.satisfy(Trace(traces)))
    }
}