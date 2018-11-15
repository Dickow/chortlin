package com.dickow.chortlin.core.test.instrumentation

import com.dickow.chortlin.core.checker.OnlineChecker
import com.dickow.chortlin.core.checker.session.InMemorySessionManager
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.exceptions.ChortlinRuntimeException
import com.dickow.chortlin.core.instrumentation.ASTInstrumentation
import com.dickow.chortlin.core.instrumentation.ByteBuddyInstrumentation
import com.dickow.chortlin.core.instrumentation.strategy.CheckInMemory
import com.dickow.chortlin.core.instrumentation.strategy.InstrumentationStrategy
import com.dickow.chortlin.core.test.shared.OnlineFirstClass
import com.dickow.chortlin.core.test.shared.OnlineSecondClass
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class OnlineInstrumentationTests {
    private val instrumentationVisitor = ASTInstrumentation(ByteBuddyInstrumentation)

    @Test
    fun `check online checker on simple choreography with manual execution`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(OnlineFirstClass::class.java, "method1"), "#1")
                .interaction(participant(OnlineFirstClass::class.java, "method2"),
                        participant(OnlineSecondClass::class.java, "method1"), "#2")
                .end()
                .runVisitor(instrumentationVisitor)
        val onlineChecker = OnlineChecker(InMemorySessionManager(listOf(choreography)))
        InstrumentationStrategy.strategy = CheckInMemory(onlineChecker, true)

        OnlineFirstClass().method1()
        OnlineFirstClass().method2()
        OnlineSecondClass().method1()
    }

    @Test
    fun `check that online checker fails fast if traces do not conform`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(OnlineFirstClass::class.java, "method1"), "#1")
                .interaction(participant(OnlineFirstClass::class.java, "method2"),
                        participant(OnlineSecondClass::class.java, "method1"), "#2")
                .end()
                .runVisitor(instrumentationVisitor)
        val onlineChecker = OnlineChecker(InMemorySessionManager(listOf(choreography)))
        InstrumentationStrategy.strategy = CheckInMemory(onlineChecker, true)

        OnlineFirstClass().method1()
        assertFailsWith(ChortlinRuntimeException::class) { OnlineSecondClass().method1() } // Out of order execution
        assertFailsWith(ChortlinRuntimeException::class) { OnlineFirstClass().method2() } // Now the entire choreography is failing
    }
}