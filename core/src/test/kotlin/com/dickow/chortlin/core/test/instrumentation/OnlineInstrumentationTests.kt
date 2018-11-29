package com.dickow.chortlin.core.test.instrumentation

import com.dickow.chortlin.core.checker.OnlineChecker
import com.dickow.chortlin.core.checker.session.InMemorySessionManager
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.external
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.correlation.factory.CorrelationFactory.addFunctions
import com.dickow.chortlin.core.correlation.factory.CorrelationFactory.correlation
import com.dickow.chortlin.core.correlation.factory.CorrelationFactory.defineCorrelationSet
import com.dickow.chortlin.core.correlation.factory.CorrelationFactory.fromInput
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
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class OnlineInstrumentationTests {
    private val instrumentationVisitor = ASTInstrumentation(ByteBuddyInstrumentation)

    private val external = external("External client")
    private val onlineFirstM1 = participant(OnlineFirstClass::class.java, "method1")
    private val onlineFirstM2 = participant(OnlineFirstClass::class.java, "method2")
    private val onlineSecondM1 = participant(OnlineSecondClass::class.java, "method1")
    private val onlineSecondM2 = participant(OnlineSecondClass::class.java, "method2")
    private val onlineThirdM1 = participant(OnlineThirdClass::class.java, "method1")
    private val onlineThirdM2 = participant(OnlineThirdClass::class.java, "method2")

    @Test
    fun `check online checker on simple choreography with manual execution`() {
        val sessionId = UUID.randomUUID()
        val cset = defineCorrelationSet()
                .add(correlation(onlineFirstM1, { sessionId }, addFunctions(fromInput { sessionId })))
                .add(correlation(onlineFirstM2, { sessionId }))
                .add(correlation(onlineSecondM1, { sessionId }))
                .finish()
        val choreography = Choreography.builder()
                .interaction(external, onlineFirstM1, "#1")
                .interaction(onlineFirstM1.nonObservable, onlineFirstM2, "#2")
                .interaction(onlineFirstM2.nonObservable, onlineSecondM1, "#3")
                .returnFrom(onlineSecondM1, "return #3")
                .end().setCorrelationSet(cset)
                .runVisitor(instrumentationVisitor)
        val onlineChecker = OnlineChecker(InMemorySessionManager(listOf(choreography)))
        InstrumentationStrategy.strategy = CheckInMemory(onlineChecker, true)

        OnlineFirstClass().method1()
        OnlineFirstClass().method2()
        OnlineSecondClass().method1()
    }

    @Test
    fun `check that online checker fails fast if traces do not conform`() {
        val sessionId = UUID.randomUUID()
        val cset = defineCorrelationSet()
                .add(correlation(onlineFirstM1, { sessionId }, addFunctions(fromInput { sessionId })))
                .add(correlation(onlineFirstM2, { sessionId }))
                .add(correlation(onlineSecondM1, { sessionId }))
                .finish()
        val choreography = Choreography.builder()
                .interaction(external, onlineFirstM1, "#1")
                .interaction(onlineFirstM1.nonObservable, onlineFirstM2, "#2")
                .interaction(onlineFirstM2.nonObservable, onlineSecondM1, "#3")
                .returnFrom(onlineSecondM1, "return #3")
                .end().setCorrelationSet(cset)
                .runVisitor(instrumentationVisitor)
        val onlineChecker = OnlineChecker(InMemorySessionManager(listOf(choreography)))
        InstrumentationStrategy.strategy = CheckInMemory(onlineChecker, true)

        OnlineFirstClass().method1()
        assertFailsWith(ChortlinRuntimeException::class) { OnlineSecondClass().method1() } // Out of order execution
        assertFailsWith(ChortlinRuntimeException::class) { OnlineFirstClass().method2() } // Now the entire choreography is failing
    }

    @Test
    fun `check that concurrently running choreographies work`() {
        val sessionId1 = UUID.randomUUID()
        val sessionId2 = UUID.randomUUID()
        val cset1 = defineCorrelationSet()
                .add(correlation(onlineFirstM1, { sessionId1 }, addFunctions(fromInput { sessionId1 })))
                .add(correlation(onlineFirstM2, { sessionId1 }))
                .add(correlation(onlineSecondM1, { sessionId1 }))
                .finish()
        val cset2 = defineCorrelationSet()
                .add(correlation(onlineSecondM2, { sessionId2 }, addFunctions(fromInput { sessionId2 })))
                .add(correlation(onlineThirdM1, { sessionId2 }))
                .add(correlation(onlineThirdM2, { sessionId2 }))
                .finish()
        val choreography1 = Choreography.builder()
                .interaction(external, onlineFirstM1, "#1")
                .interaction(onlineFirstM1.nonObservable, onlineFirstM2, "#2")
                .interaction(onlineFirstM2.nonObservable, onlineSecondM1, "#3")
                .returnFrom(onlineSecondM1, "return #3")
                .end().setCorrelationSet(cset1)
                .runVisitor(instrumentationVisitor)

        val choreography2 = Choreography.builder()
                .interaction(external, onlineSecondM2, "#1")
                .interaction(onlineSecondM2.nonObservable, onlineThirdM1, "#2")
                .interaction(onlineThirdM1.nonObservable, onlineThirdM2, "#3")
                .end().setCorrelationSet(cset2)
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
        val sessionId1 = UUID.randomUUID()
        val sessionId2 = UUID.randomUUID()
        val cset1 = defineCorrelationSet()
                .add(correlation(onlineFirstM1, { sessionId1 }, addFunctions(fromInput { sessionId1 })))
                .add(correlation(onlineFirstM2, { sessionId1 }))
                .add(correlation(onlineSecondM1, { sessionId1 }))
                .finish()
        val cset2 = defineCorrelationSet()
                .add(correlation(onlineSecondM2, { sessionId2 }, addFunctions(fromInput { sessionId2 })))
                .add(correlation(onlineThirdM1, { sessionId2 }))
                .add(correlation(onlineThirdM2, { sessionId2 }))
                .finish()

        val choreography1 = Choreography.builder()
                .interaction(external, onlineFirstM1, "#1")
                .interaction(onlineFirstM1.nonObservable, onlineFirstM2, "#2")
                .interaction(onlineFirstM2.nonObservable, onlineSecondM1, "#3")
                .returnFrom(onlineSecondM1, "return #3")
                .end().setCorrelationSet(cset1)
                .runVisitor(instrumentationVisitor)

        val choreography2 = Choreography.builder()
                .interaction(external, onlineSecondM2, "#1")
                .interaction(onlineSecondM2.nonObservable, onlineThirdM1, "#2")
                .interaction(onlineThirdM1.nonObservable, onlineThirdM2, "#3")
                .end().setCorrelationSet(cset2)
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