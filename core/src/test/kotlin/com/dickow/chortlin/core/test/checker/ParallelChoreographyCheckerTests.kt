package com.dickow.chortlin.core.test.checker

import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.test.shared.ParallelClassA
import com.dickow.chortlin.core.test.shared.ParallelClassB
import com.dickow.chortlin.core.test.shared.ParallelClassC
import com.dickow.chortlin.core.trace.Invocation
import com.dickow.chortlin.core.trace.Trace
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ParallelChoreographyCheckerTests {

    @Test
    fun `create checker for parallel choreography and test that traces with mixed ordering works`() {
        val checker = Choreography.builder()
                .foundMessage(participant(ParallelClassA::class.java, "method1"), "A:1")
                .parallel { c ->
                    c
                            .interaction(
                                    participant(ParallelClassB::class.java, "method1"),
                                    participant(ParallelClassB::class.java, "method2"),
                                    "B:1->2")
                            .end()
                            .build()
                }
                .interaction(
                        participant(ParallelClassC::class.java, "method1"),
                        participant(ParallelClassC::class.java, "method2"),
                        "C:1->2")
                .end()
                .build()
                .createChecker()

        val traceElements = arrayOf(
                Invocation(participant(ParallelClassA::class.java, "method1")),
                Invocation(participant(ParallelClassC::class.java, "method1")),
                Invocation(participant(ParallelClassB::class.java, "method1")),
                Invocation(participant(ParallelClassC::class.java, "method2")),
                Invocation(participant(ParallelClassB::class.java, "method2")))
        val trace = Trace(traceElements)
        assertTrue(checker.check(trace))
        assertEquals(0, trace.getNotConsumed().size)
    }

    @Test
    fun `check that traces with invalid causal ordering fails`() {
        val checker = Choreography.builder()
                .foundMessage(participant(ParallelClassA::class.java, "method1"), "A:1")
                .parallel { c ->
                    c
                            .interaction(
                                    participant(ParallelClassB::class.java, "method1"),
                                    participant(ParallelClassB::class.java, "method2"),
                                    "B:1->2")
                            .end()
                            .build()
                }
                .interaction(
                        participant(ParallelClassC::class.java, "method1"),
                        participant(ParallelClassC::class.java, "method2"),
                        "C:1->2")
                .end()
                .build()
                .createChecker()

        val traceElements = arrayOf(
                Invocation(participant(ParallelClassA::class.java, "method1")),
                Invocation(participant(ParallelClassC::class.java, "method1")),
                Invocation(participant(ParallelClassB::class.java, "method2")), // <-- Causal order error
                Invocation(participant(ParallelClassC::class.java, "method2")),
                Invocation(participant(ParallelClassB::class.java, "method1"))) // <-- Should be before B::2
        val trace = Trace(traceElements)
        assertFalse(checker.check(trace))
    }
}