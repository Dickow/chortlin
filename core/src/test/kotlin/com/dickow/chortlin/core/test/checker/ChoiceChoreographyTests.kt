package com.dickow.chortlin.core.test.checker

import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.test.shared.ChoiceClassA
import com.dickow.chortlin.core.test.shared.ChoiceClassB
import com.dickow.chortlin.core.trace.Invocation
import com.dickow.chortlin.core.trace.Trace
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ChoiceChoreographyTests {

    @Test
    @Disabled
    fun `check for root choice choreography`() {
        val checker = Choreography.builder()
                .choice({ c -> c.foundMessage(participant(ChoiceClassA::class.java, "method1"), "receive on A").end() },
                        { c -> c.foundMessage(participant(ChoiceClassB::class.java, "method1"), "receive on B").end() })
                .createChecker()
        val trace = Trace(arrayOf(Invocation(participant(ChoiceClassA::class.java, "method1"))))
        assertTrue(checker.check(trace))
    }

    @Test
    fun `ensure that trace is not valid if it has non-consumed traces after choice`() {
        val checker = Choreography.builder()
                .choice({ c -> c.foundMessage(participant(ChoiceClassA::class.java, "method1"), "receive on A").end() },
                        { c -> c.foundMessage(participant(ChoiceClassB::class.java, "method1"), "receive on B").end() })
                .createChecker()
        val trace = Trace(arrayOf(
                Invocation(participant(ChoiceClassA::class.java, "method1")),
                Invocation(participant(ChoiceClassB::class.java, "method1"))))
        assertFalse(checker.check(trace))
    }
}