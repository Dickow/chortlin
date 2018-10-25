package com.dickow.chortlin.core.test.checker

import com.dickow.chortlin.core.checker.pattern.DoublePattern
import com.dickow.chortlin.core.checker.pattern.EmptyPattern
import com.dickow.chortlin.core.checker.pattern.SinglePattern
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.test.shared.A
import com.dickow.chortlin.core.test.shared.B
import com.dickow.chortlin.core.trace.Invocation
import com.dickow.chortlin.core.trace.Return
import com.dickow.chortlin.core.trace.Trace
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ChoreographyCheckerTests {

    @Test
    fun `check pattern matches expected structure`(){
        val choreography = Choreography.builder()
                .foundMessage(participant(A::class.java, "receive"), "receive")
                .interaction(participant(A::class.java, "b"),
                        participant(B::class.java, "b"),
                        "delegate processing")
                .end()
                .build()

        DoublePattern(Invocation(participant(A::class.java, "b")),
                Invocation(participant(B::class.java, "b")),
                mutableListOf())
        val checker = choreography.createChecker()
        val firstNode = SinglePattern(Invocation(participant(A::class.java, "receive")), mutableListOf())
        val secondNode = DoublePattern(Invocation(participant(A::class.java, "b")),
                Invocation(participant(B::class.java, "b")),
                mutableListOf())
        val thirdNode = EmptyPattern(mutableListOf())
        firstNode.addChild(secondNode)
        secondNode.addChild(thirdNode)
        assertEquals(firstNode, checker.pattern)
    }

    @Test
    fun `check pattern structure for single element choreography`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(A::class.java, "receive"), "receive")
                .end()
                .build()
        val checker = choreography.createChecker()
        val end = EmptyPattern(LinkedList())
        val expected = SinglePattern(Invocation(participant(A::class.java, "receive")), LinkedList())
        expected.addChild(end)
        assertEquals(expected, checker.pattern)
    }

    @Test
    fun `check that trace conforms to configured choreography`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(A::class.java, "receive"), "receive")
                .end()
                .build()
        val trace = Trace(arrayOf(Invocation(participant(A::class.java, "receive"))))
        val checker = choreography.createChecker()
        assertTrue(checker.check(trace))
        assertTrue(checker.check(trace))
    }

    @Test
    fun `check that single end is always valid`() {
        val choreography = Choreography.builder().end().build()
        val trace = Trace(emptyArray())
        val checker = choreography.createChecker()
        assert(checker.check(trace))

        val nonEmptyTrace = Trace(arrayOf(Invocation(participant(A::class.java, "receive"))))
        assertTrue(checker.check(nonEmptyTrace))
    }

    @Test
    fun `check that full choreography matches the example trace`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(A::class.java, "receive"), "receive")
                .interaction(participant(A::class.java, "b"),
                        participant(B::class.java, "b"),
                        "delegate processing")
                .end()
                .build()
        val trace = Trace(arrayOf(
                Invocation(participant(A::class.java, "receive")),
                Invocation(participant(A::class.java, "b")),
                Invocation(participant(B::class.java, "b"))))

        val checker = choreography.createChecker()
        assertTrue(checker.check(trace))
        assertTrue(checker.check(trace))
    }

    @Test
    fun `check that trace in wrong order is not accepted`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(A::class.java, "receive"), "receive")
                .interaction(participant(A::class.java, "b"),
                        participant(B::class.java, "b"),
                        "delegate processing")
                .end()
                .build()
        val trace = Trace(arrayOf(
                Invocation(participant(A::class.java, "b")),
                Invocation(participant(A::class.java, "receive")),
                Invocation(participant(B::class.java, "b"))))

        val checker = choreography.createChecker()
        assertFalse(checker.check(trace))
        assertFalse(checker.check(trace))
    }

    @Test
    fun `check that return is validated for traces`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(A::class.java, "receive"), "receive")
                .interaction(participant(A::class.java, "b"),
                        participant(B::class.java, "b"),
                        "delegate processing")
                .interactionReturn(participant(A::class.java, "b"),
                        participant(B::class.java, "b"),
                        "delegate processing return")
                .end()
                .build()
        val checker = choreography.createChecker()
        val trace = Trace(arrayOf(
                Invocation(participant(A::class.java, "receive")),
                Invocation(participant(A::class.java, "b")),
                Invocation(participant(B::class.java, "b")),
                Return(participant(B::class.java, "b"))))
        assertTrue(checker.check(trace))
    }

    @Test
    fun `check that found message with return trace is accepted`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(A::class.java, "receive"), "receive")
                .foundMessageReturn(participant(A::class.java, "receive"), "return")
                .end()
                .build()
        val checker = choreography.createChecker()
        val trace = Trace(arrayOf(
                Invocation(participant(A::class.java, "receive")),
                Return(participant(A::class.java, "receive"))))
        assertTrue(checker.check(trace))
    }

    @Test
    fun `check that trace is not valid when return is missing`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(A::class.java, "receive"), "receive")
                .foundMessageReturn(participant(A::class.java, "receive"), "return")
                .end()
                .build()
        val checker = choreography.createChecker()
        val trace = Trace(arrayOf(Invocation(participant(A::class.java, "receive"))))
        assertFalse(checker.check(trace))
    }

    @Test
    fun `check that trace is not valid when return from wrong participant is encountered`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(A::class.java, "receive"), "receive")
                .foundMessageReturn(participant(A::class.java, "receive"), "return")
                .end()
                .build()
        val checker = choreography.createChecker()
        val trace = Trace(arrayOf(
                Invocation(participant(A::class.java, "receive")),
                Return(participant(B::class.java, "b"))))
        assertFalse(checker.check(trace))
    }

}