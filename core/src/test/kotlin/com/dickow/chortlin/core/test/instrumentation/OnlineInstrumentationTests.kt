package com.dickow.chortlin.core.test.instrumentation

import com.dickow.chortlin.core.checker.OnlineChecker
import com.dickow.chortlin.core.checker.session.InMemorySessionManager
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.external
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.correlation.factory.CorrelationFactory.correlation
import com.dickow.chortlin.core.correlation.factory.CorrelationFactory.defineCorrelation
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
    private val onlineFirstClass = participant(OnlineFirstClass::class.java)
    private val onlineSecondClass = participant(OnlineSecondClass::class.java)
    private val onlineThirdClass = participant(OnlineThirdClass::class.java)

    @Test
    fun `check online checker on simple choreography with manual execution`() {
        val sessionId = UUID.randomUUID()
        val cset = defineCorrelation()
                .add(correlation(onlineFirstClass.onMethod("method1", OnlineFirstClass::method1), "sid", { sessionId })
                        .extendFromInput("sid") { sessionId }.done())
                .add(correlation(onlineFirstClass.onMethod("method2", OnlineFirstClass::method2), "sid", { sessionId })
                        .noExtensions())
                .add(correlation(onlineSecondClass.onMethod("method1", OnlineSecondClass::method1), "sid", { sessionId })
                        .noExtensions())
                .finish()
        val choreography = Choreography.builder()
                .interaction(external, onlineFirstClass.onMethod("method1"), "#1")
                .interaction(onlineFirstClass, onlineFirstClass.onMethod("method2"), "#2")
                .interaction(onlineFirstClass, onlineSecondClass.onMethod("method1"), "#3")
                .returnFrom(onlineSecondClass.onMethod("method1"), "return #3")
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
        val cset = defineCorrelation()
                .add(correlation(onlineFirstClass.onMethod("method1", OnlineFirstClass::method1), "sid", { sessionId })
                        .extendFromInput("sid") { sessionId }.done())
                .add(correlation(onlineFirstClass.onMethod("method2", OnlineFirstClass::method2), "sid", { sessionId })
                        .noExtensions())
                .add(correlation(onlineSecondClass.onMethod("method1", OnlineSecondClass::method1), "sid", { sessionId })
                        .noExtensions())
                .finish()
        val choreography = Choreography.builder()
                .interaction(external, onlineFirstClass.onMethod("method1"), "#1")
                .interaction(onlineFirstClass, onlineFirstClass.onMethod("method2"), "#2")
                .interaction(onlineFirstClass, onlineSecondClass.onMethod("method1"), "#3")
                .returnFrom(onlineSecondClass.onMethod("method1"), "return #3")
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
        val cset1 = defineCorrelation()
                .add(correlation(onlineFirstClass.onMethod("method1", OnlineFirstClass::method1), "sid", { sessionId1 })
                        .extendFromInput("sid") { sessionId1 }.done())
                .add(correlation(onlineFirstClass.onMethod("method2", OnlineFirstClass::method2), "sid", { sessionId1 })
                        .noExtensions())
                .add(correlation(onlineSecondClass.onMethod("method1", OnlineSecondClass::method1), "sid", { sessionId1 })
                        .noExtensions())
                .finish()
        val cset2 = defineCorrelation()
                .add(correlation(onlineSecondClass.onMethod("method2", OnlineSecondClass::method2), "sid", { sessionId2 })
                        .extendFromInput("sid") { sessionId2 }.done())
                .add(correlation(onlineThirdClass.onMethod("method1", OnlineThirdClass::method1), "sid", { sessionId2 })
                        .noExtensions())
                .add(correlation(onlineThirdClass.onMethod("method2", OnlineThirdClass::method2), "sid", { sessionId2 })
                        .noExtensions())
                .finish()
        val choreography1 = Choreography.builder()
                .interaction(external, onlineFirstClass.onMethod("method1"), "#1")
                .interaction(onlineFirstClass, onlineFirstClass.onMethod("method2"), "#2")
                .interaction(onlineFirstClass, onlineSecondClass.onMethod("method1"), "#3")
                .returnFrom(onlineSecondClass.onMethod("method1"), "return #3")
                .end().setCorrelationSet(cset1)
                .runVisitor(instrumentationVisitor)

        val choreography2 = Choreography.builder()
                .interaction(external, onlineSecondClass.onMethod("method2"), "#1")
                .interaction(onlineSecondClass, onlineThirdClass.onMethod("method1"), "#2")
                .interaction(onlineThirdClass, onlineThirdClass.onMethod("method2"), "#3")
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
        val cset1 = defineCorrelation()
                .add(correlation(onlineFirstClass.onMethod("method1", OnlineFirstClass::method1), "sid", { sessionId1 })
                        .extendFromInput("sid") { sessionId1 }.done())
                .add(correlation(onlineFirstClass.onMethod("method2", OnlineFirstClass::method2), "sid", { sessionId1 })
                        .noExtensions())
                .add(correlation(onlineSecondClass.onMethod("method1", OnlineSecondClass::method1), "sid", { sessionId1 })
                        .noExtensions())
                .finish()
        val cset2 = defineCorrelation()
                .add(correlation(onlineSecondClass.onMethod("method2", OnlineSecondClass::method2), "sid", { sessionId2 })
                        .extendFromInput("sid") { sessionId2 }.done())
                .add(correlation(onlineThirdClass.onMethod("method1", OnlineThirdClass::method1), "sid", { sessionId2 })
                        .noExtensions())
                .add(correlation(onlineThirdClass.onMethod("method2", OnlineThirdClass::method2), "sid", { sessionId2 })
                        .noExtensions())
                .finish()

        val choreography1 = Choreography.builder()
                .interaction(external, onlineFirstClass.onMethod("method1"), "#1")
                .interaction(onlineFirstClass, onlineFirstClass.onMethod("method2"), "#2")
                .interaction(onlineFirstClass, onlineSecondClass.onMethod("method1"), "#3")
                .returnFrom(onlineSecondClass.onMethod("method1"), "return #3")
                .end().setCorrelationSet(cset1)
                .runVisitor(instrumentationVisitor)

        val choreography2 = Choreography.builder()
                .interaction(external, onlineSecondClass.onMethod("method2"), "#1")
                .interaction(onlineSecondClass, onlineThirdClass.onMethod("method1"), "#2")
                .interaction(onlineThirdClass, onlineThirdClass.onMethod("method2"), "#3")
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