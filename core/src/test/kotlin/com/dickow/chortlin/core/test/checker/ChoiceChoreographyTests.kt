package com.dickow.chortlin.core.test.checker

import com.dickow.chortlin.core.checker.result.CheckResult
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.test.shared.ChoiceClassA
import com.dickow.chortlin.core.test.shared.ChoiceClassB
import com.dickow.chortlin.core.trace.Invocation
import com.dickow.chortlin.core.trace.Trace
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@Disabled
class ChoiceChoreographyTests {

    @Test
    fun `check for root choice choreography`() {
        val checker = Choreography.builder()
                .choice({ c -> c.foundMessage(participant(ChoiceClassA::class.java, "method1"), "receive on A").end() },
                        { c -> c.foundMessage(participant(ChoiceClassB::class.java, "method1"), "receive on B").end() })
                .createChecker()
        val trace = Trace(listOf(Invocation(participant(ChoiceClassA::class.java, "method1"))))
        assertEquals(CheckResult.Full, checker.check(trace))
    }

    @Test
    fun `ensure that trace is not valid if it has non-consumed traces after choice`() {
        val checker = Choreography.builder()
                .choice({ c -> c.foundMessage(participant(ChoiceClassA::class.java, "method1"), "receive on A").end() },
                        { c -> c.foundMessage(participant(ChoiceClassB::class.java, "method1"), "receive on B").end() })
                .createChecker()
        val trace = Trace(listOf(
                Invocation(participant(ChoiceClassA::class.java, "method1")),
                Invocation(participant(ChoiceClassB::class.java, "method1"))))
        assertEquals(CheckResult.None, checker.check(trace))
    }
}