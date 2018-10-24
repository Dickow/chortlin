package com.dickow.chortlin.core.test.checker

import com.dickow.chortlin.core.checker.ChoreographyChecker
import com.dickow.chortlin.core.checker.pattern.DoublePattern
import com.dickow.chortlin.core.checker.pattern.EmptyPattern
import com.dickow.chortlin.core.checker.pattern.SinglePattern
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.test.shared.A
import com.dickow.chortlin.core.test.shared.B
import com.dickow.chortlin.core.trace.Trace
import com.dickow.chortlin.core.trace.TraceElement
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

        DoublePattern(TraceElement(participant(A::class.java, "b")),
                TraceElement(participant(B::class.java, "b")),
                mutableListOf())
        val checker = choreography.createChecker()
        val firstNode = SinglePattern(TraceElement(participant(A::class.java, "receive")), mutableListOf())
        val secondNode = DoublePattern(TraceElement(participant(A::class.java, "b")),
                TraceElement(participant(B::class.java, "b")),
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
                .build()
        val checker = ChoreographyChecker(choreography)
        val expected = SinglePattern(TraceElement(participant(A::class.java, "receive")), LinkedList())
        assertEquals(expected, checker.pattern)
    }

    @Test
    fun `check that trace conforms to configured choreography`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(A::class.java, "receive"), "receive")
                .build()
        val trace = Trace(arrayOf(TraceElement(participant(A::class.java, "receive"))))
        val checker = ChoreographyChecker(choreography)
        assertTrue(checker.check(trace))
        assertTrue(checker.check(trace))
    }

    @Test
    fun `check that single end is always valid`() {
        val choreography = Choreography.builder().end().build()
        val trace = Trace(emptyArray())
        val checker = choreography.createChecker()
        assert(checker.check(trace))

        val nonEmptyTrace = Trace(arrayOf(TraceElement(participant(A::class.java, "receive"))))
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
                TraceElement(participant(A::class.java, "receive")),
                TraceElement(participant(A::class.java, "b")),
                TraceElement(participant(B::class.java, "b"))))

        val checker = choreography.createChecker()
        assertTrue(checker.check(trace))
        assertTrue(checker.check(trace))
    }

    @Test
    fun `check that invalid trace is not accepted`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(A::class.java, "receive"), "receive")
                .interaction(participant(A::class.java, "b"),
                        participant(B::class.java, "b"),
                        "delegate processing")
                .end()
                .build()
        val trace = Trace(arrayOf(
                TraceElement(participant(A::class.java, "b")),
                TraceElement(participant(A::class.java, "receive")),
                TraceElement(participant(B::class.java, "b"))))

        val checker = choreography.createChecker()
        assertFalse(checker.check(trace))
        assertFalse(checker.check(trace))
    }


}