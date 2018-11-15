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
import com.dickow.chortlin.core.test.shared.OnlineThirdClass
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class OnlineInstrumentationTests {
    private val instrumentationVisitor = ASTInstrumentation(ByteBuddyInstrumentation)

    @Test
    fun `check online checker on simple choreography with manual execution`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(OnlineFirstClass::class.java, "method1"), "#1")
                .interaction(participant(OnlineFirstClass::class.java, "method2"),
                        participant(OnlineSecondClass::class.java, "method1"), "#2")
                .returnFrom(participant(OnlineSecondClass::class.java, "method1"), "return #2")
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

    @Test
    fun `check that concurrently running choreographies work`() {
        val choreography1 = Choreography.builder()
                .foundMessage(participant(OnlineFirstClass::class.java, "method1"), "#1")
                .interaction(participant(OnlineFirstClass::class.java, "method2"),
                        participant(OnlineSecondClass::class.java, "method1"),
                        "#2")
                .returnFrom(participant(OnlineSecondClass::class.java, "method1"), "return #2")
                .end()
                .runVisitor(instrumentationVisitor)

        val choreography2 = Choreography.builder()
                .foundMessage(participant(OnlineSecondClass::class.java, "method2"), "#1")
                .interaction(participant(OnlineThirdClass::class.java, "method1"),
                        participant(OnlineThirdClass::class.java, "method2"),
                        "#2")
                .end()
                .runVisitor(instrumentationVisitor)

        val onlineChecker = OnlineChecker(InMemorySessionManager(listOf(choreography1, choreography2)))
        InstrumentationStrategy.strategy = CheckInMemory(onlineChecker, true)

        val thread1 = GlobalScope.async {
            OnlineFirstClass().method1()
            OnlineFirstClass().method2()
            OnlineSecondClass().method1()
        }

        val thread2 = GlobalScope.async {
            OnlineSecondClass().method2()
            OnlineThirdClass().method1()
            OnlineThirdClass().method2()
        }

        runBlocking { awaitAll(thread1, thread2) }
    }

    @Test
    fun `check that concurrently running choreographies work and throw exception with wrong execution`() {
        val choreography1 = Choreography.builder()
                .foundMessage(participant(OnlineFirstClass::class.java, "method1"), "#1")
                .interaction(participant(OnlineFirstClass::class.java, "method2"),
                        participant(OnlineSecondClass::class.java, "method1"),
                        "#2")
                .returnFrom(participant(OnlineSecondClass::class.java, "method1"), "return #2")
                .end()
                .runVisitor(instrumentationVisitor)

        val choreography2 = Choreography.builder()
                .foundMessage(participant(OnlineSecondClass::class.java, "method2"), "#1")
                .interaction(participant(OnlineThirdClass::class.java, "method1"),
                        participant(OnlineThirdClass::class.java, "method2"),
                        "#2")
                .end()
                .runVisitor(instrumentationVisitor)

        val onlineChecker = OnlineChecker(InMemorySessionManager(listOf(choreography1, choreography2)))
        InstrumentationStrategy.strategy = CheckInMemory(onlineChecker, true)

        val thread1 = GlobalScope.async {
            OnlineFirstClass().method1()
            OnlineSecondClass().method1() // Wrong execution order
            OnlineFirstClass().method2()

        }

        val thread2 = GlobalScope.async {
            OnlineSecondClass().method2()
            OnlineThirdClass().method1()
            OnlineThirdClass().method2()
        }

        val atomicBoolean = AtomicBoolean()
        atomicBoolean.set(false)
        runBlocking {
            try {
                thread1.await()
            } catch (e: ChortlinRuntimeException) {
                atomicBoolean.set(true)
            }
            thread2.await()
        }
        assertTrue(atomicBoolean.get(), "Expected exception of type ${ChortlinRuntimeException::class}")
    }
}