package com.dickow.chortlin.test.application.checker

import com.dickow.chortlin.checker.checker.OnlineChecker
import com.dickow.chortlin.checker.checker.result.ChoreographyStatus
import com.dickow.chortlin.checker.checker.session.InMemorySessionManager
import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.external
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.checker.correlation.factory.CorrelationFactory.correlation
import com.dickow.chortlin.checker.correlation.factory.CorrelationFactory.defineCorrelation
import com.dickow.chortlin.checker.correlation.factory.PathBuilderFactory.root
import com.dickow.chortlin.shared.exceptions.ChoreographyRuntimeException
import com.dickow.chortlin.test.application.shared.OnlineFirstClass
import com.dickow.chortlin.test.application.shared.OnlineSecondClass
import com.dickow.chortlin.test.application.shared.OnlineThirdClass
import com.dickow.chortlin.test.application.shared.builder.TestObservableBuilder.buildInvocation
import com.dickow.chortlin.test.application.shared.builder.TestObservableBuilder.buildReturn
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class OnlineCheckerTests {
    private val external = external("External client")
    private val onlineFirst = participant(OnlineFirstClass::class.java)
    private val onlineSecond = participant(OnlineSecondClass::class.java)
    private val onlineThird = participant(OnlineThirdClass::class.java)

    private val cdef = defineCorrelation()
            .add(correlation(onlineFirst.onMethod("method1"), "sid", root().build())
                    .extendFromInput("sid", root().build()).done())
            .add(correlation(onlineFirst.onMethod("method2"), "sid", root().build()).noExtensions())
            .add(correlation(onlineSecond.onMethod("method1"), "sid", root().build()).noExtensions())
            .add(correlation(onlineSecond.onMethod("method2"), "sid", root().build()).noExtensions())
            .add(correlation(onlineThird.onMethod("method1"), "sid", root().build()).noExtensions())
            .add(correlation(onlineThird.onMethod("method2"), "sid", root().build()).noExtensions())
            .finish()

    private val allArguments = arrayOf<Any?>()
    private val returnValue = Any()

    private val choreography = Choreography.builder()
            .interaction(external, onlineFirst.onMethod("method1"), "#1")
            .interaction(onlineFirst, onlineFirst.onMethod("method2"), "#2")
            .interaction(onlineFirst, onlineSecond.onMethod("method1"), "#3")
            .interaction(onlineSecond, onlineSecond.onMethod("method2"), "#4")
            .interaction(onlineSecond, onlineThird.onMethod("method1"), "#5")
            .interaction(onlineThird, onlineThird.onMethod("method2"), "#6")
            .returnFrom(onlineThird.onMethod("method2"), "return #6")
            .returnFrom(onlineThird.onMethod("method1"), "return #5")
            .returnFrom(onlineSecond.onMethod("method2"), "return #4")
            .returnFrom(onlineSecond.onMethod("method1"), "return #3")
            .returnFrom(onlineFirst.onMethod("method2"), "return #2")
            .returnFrom(onlineFirst.onMethod("method1"), "return #1")
            .end()
            .setCorrelation(cdef)

    private val expectedTraceSequence = listOf(
            buildInvocation(OnlineFirstClass::class.java, "method1", allArguments),
            buildInvocation(OnlineFirstClass::class.java, "method2", allArguments),
            buildInvocation(OnlineSecondClass::class.java, "method1", allArguments),
            buildInvocation(OnlineSecondClass::class.java, "method2", allArguments),
            buildInvocation(OnlineThirdClass::class.java, "method1", allArguments),
            buildInvocation(OnlineThirdClass::class.java, "method2", allArguments),
            buildReturn(OnlineThirdClass::class.java, "method2", allArguments, returnValue),
            buildReturn(OnlineThirdClass::class.java, "method1", allArguments, returnValue),
            buildReturn(OnlineSecondClass::class.java, "method2", allArguments, returnValue),
            buildReturn(OnlineSecondClass::class.java, "method1", allArguments, returnValue),
            buildReturn(OnlineFirstClass::class.java, "method2", allArguments, returnValue),
            buildReturn(OnlineFirstClass::class.java, "method1", allArguments, returnValue)
    )

    @Test
    fun `check correctness of online checker when simulating execution`() {
        val onlineChecker = OnlineChecker(InMemorySessionManager(listOf(choreography)))
        val allExceptLastTrace = expectedTraceSequence.subList(0, expectedTraceSequence.size - 1)
        allExceptLastTrace.forEach { trace -> assertEquals(ChoreographyStatus.IN_PROGRESS, onlineChecker.check(trace)) }
        assertEquals(ChoreographyStatus.FINISHED, onlineChecker.check(expectedTraceSequence.last()))
    }

    @Test
    fun `ensure that out of order execution is rejected`() {
        val onlineChecker = OnlineChecker(InMemorySessionManager(listOf(choreography)))
        assertEquals(ChoreographyStatus.IN_PROGRESS,
                onlineChecker.check(buildInvocation(OnlineFirstClass::class.java, "method1", allArguments)))
        assertEquals(ChoreographyStatus.IN_PROGRESS,
                onlineChecker.check(buildInvocation(OnlineFirstClass::class.java, "method2", allArguments)))
        assertEquals(ChoreographyStatus.IN_PROGRESS,
                onlineChecker.check(buildInvocation(OnlineSecondClass::class.java, "method1", allArguments)))

        // Apply the out of order execution
        assertFailsWith(ChoreographyRuntimeException::class) {
            onlineChecker.check(buildInvocation(OnlineThirdClass::class.java, "method2", allArguments))
        }
    }

    @Test
    fun `check that checker can start over once a session finished successfully`() {
        val onlineChecker = OnlineChecker(InMemorySessionManager(listOf(choreography)))
        expectedTraceSequence.forEach { traceElement -> onlineChecker.check(traceElement) }

        val allExceptLastTrace = expectedTraceSequence.subList(0, expectedTraceSequence.size - 1)
        allExceptLastTrace.forEach { trace -> assertEquals(ChoreographyStatus.IN_PROGRESS, onlineChecker.check(trace)) }
        assertEquals(ChoreographyStatus.FINISHED, onlineChecker.check(expectedTraceSequence.last()))
    }

    @Test
    fun `check that a new instance can start when one has failed`() {
        val onlineChecker = OnlineChecker(InMemorySessionManager(listOf(choreography)))
        onlineChecker.check(buildInvocation(OnlineFirstClass::class.java, "method1", allArguments))
        onlineChecker.check(buildInvocation(OnlineFirstClass::class.java, "method2", allArguments))
        onlineChecker.check(buildInvocation(OnlineSecondClass::class.java, "method1", allArguments))

        // Apply the out of order execution
        assertFailsWith(ChoreographyRuntimeException::class) {
            onlineChecker.check(buildInvocation(OnlineThirdClass::class.java, "method2", allArguments))
        }

        // Then check that a new instance can run
        val allExceptLastTrace = expectedTraceSequence.subList(0, expectedTraceSequence.size - 1)
        allExceptLastTrace.forEach { trace -> assertEquals(ChoreographyStatus.IN_PROGRESS, onlineChecker.check(trace)) }
        assertEquals(ChoreographyStatus.FINISHED, onlineChecker.check(expectedTraceSequence.last()))
    }

    @Test
    fun `check that online checker works for unrelated choreographies with interleaved execution`() {
        val cset1 = defineCorrelation()
                .add(correlation(onlineFirst.onMethod("method1"), "sid", root().build())
                        .extendFromInput("sid", root().build()).done())
                .add(correlation(onlineFirst.onMethod("method2"), "sid", root().build()).noExtensions())
                .add(correlation(onlineSecond.onMethod("method1"), "sid", root().build()).noExtensions())
                .finish()
        val cset2 = defineCorrelation()
                .add(correlation(onlineSecond.onMethod("method2"), "sid", root().build())
                        .extendFromInput("sid", root().build()).done())
                .add(correlation(onlineThird.onMethod("method1"), "sid", root().build()).noExtensions())
                .add(correlation(onlineThird.onMethod("method2"), "sid", root().build()).noExtensions())
                .finish()

        val choreography1 = Choreography.builder()
                .interaction(external, onlineFirst.onMethod("method1"), "#1")
                .interaction(onlineFirst, onlineFirst.onMethod("method2"), "#2")
                .interaction(onlineFirst, onlineSecond.onMethod("method1"), "#3")
                .returnFrom(onlineSecond.onMethod("method1"), "return #3")
                .end().setCorrelation(cset1)

        val choreography2 = Choreography.builder()
                .interaction(external, onlineSecond.onMethod("method2"), "#1")
                .interaction(onlineSecond, onlineThird.onMethod("method1"), "#2")
                .interaction(onlineThird, onlineThird.onMethod("method2"), "#3")
                .end().setCorrelation(cset2)
        val checker = OnlineChecker(InMemorySessionManager(listOf(choreography1, choreography2)))
        assertEquals(ChoreographyStatus.IN_PROGRESS,
                checker.check(buildInvocation(OnlineFirstClass::class.java, "method1", allArguments))) // Choreography 1
        assertEquals(ChoreographyStatus.IN_PROGRESS,
                checker.check(buildInvocation(OnlineSecondClass::class.java, "method2", allArguments))) // Choreography 2
        assertEquals(ChoreographyStatus.IN_PROGRESS,
                checker.check(buildInvocation(OnlineFirstClass::class.java, "method2", allArguments))) // Choreography 1
        assertEquals(ChoreographyStatus.IN_PROGRESS,
                checker.check(buildInvocation(OnlineSecondClass::class.java, "method1", allArguments))) // Choreography 1
        assertEquals(ChoreographyStatus.IN_PROGRESS,
                checker.check(buildInvocation(OnlineThirdClass::class.java, "method1", allArguments))) // Choreography 2
        assertEquals(ChoreographyStatus.FINISHED,
                checker.check(buildReturn(OnlineSecondClass::class.java, "method1", allArguments, returnValue))) // Choreography 1
        assertEquals(ChoreographyStatus.FINISHED,
                checker.check(buildInvocation(OnlineThirdClass::class.java, "method2", allArguments))) // Choreography 2
    }
}