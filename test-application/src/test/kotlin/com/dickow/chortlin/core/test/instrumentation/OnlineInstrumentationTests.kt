package com.dickow.chortlin.core.test.instrumentation

import com.dickow.chortlin.checker.checker.ChoreographyChecker
import com.dickow.chortlin.checker.checker.factory.OnlineCheckerFactory
import com.dickow.chortlin.checker.checker.result.CheckResult
import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.external
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.checker.correlation.factory.CorrelationFactory.correlation
import com.dickow.chortlin.checker.correlation.factory.CorrelationFactory.defineCorrelation
import com.dickow.chortlin.core.test.shared.OnlineInstrumentFirstClass
import com.dickow.chortlin.core.test.shared.OnlineInstrumentSecondClass
import com.dickow.chortlin.core.test.shared.OnlineInstrumentThirdClass
import com.dickow.chortlin.interception.configuration.InterceptionConfiguration
import com.dickow.chortlin.interception.sending.TraceSender
import com.dickow.chortlin.shared.exceptions.ChoreographyRuntimeException
import com.dickow.chortlin.shared.trace.TraceElement
import com.dickow.chortlin.shared.trace.dto.InvocationDTO
import com.dickow.chortlin.shared.trace.dto.ReturnDTO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class OnlineInstrumentationTests {
    private val external = external("External client")
    private val onlineFirstClass = participant(OnlineInstrumentFirstClass::class.java)
    private val onlineSecondClass = participant(OnlineInstrumentSecondClass::class.java)
    private val onlineThirdClass = participant(OnlineInstrumentThirdClass::class.java)

    @Test
    fun `check online checker on simple choreography with manual execution`() {
        val sessionId = UUID.randomUUID()
        val cset = defineCorrelation()
                .add(correlation(onlineFirstClass.onMethod("method1", OnlineInstrumentFirstClass::method1),
                        "sid", { sessionId })
                        .extendFromInput("sid") { sessionId }.done())
                .add(correlation(onlineFirstClass.onMethod("method2", OnlineInstrumentFirstClass::method2),
                        "sid", { sessionId })
                        .noExtensions())
                .add(correlation(onlineSecondClass.onMethod("method1", OnlineInstrumentSecondClass::method1),
                        "sid", { sessionId })
                        .noExtensions())
                .finish()
        val choreography = Choreography.builder()
                .interaction(external, onlineFirstClass.onMethod("method1"), "#1")
                .interaction(onlineFirstClass, onlineFirstClass.onMethod("method2"), "#2")
                .interaction(onlineFirstClass, onlineSecondClass.onMethod("method1"), "#3")
                .returnFrom(onlineSecondClass.onMethod("method1"), "return #3")
                .end().setCorrelation(cset)
        val checker = InterceptingTestChecker(OnlineCheckerFactory.createOnlineChecker(listOf(choreography)))
        val sender = TestSender(checker)
        InterceptionConfiguration.setupInterception(sender)

        OnlineInstrumentFirstClass().method1()
        OnlineInstrumentFirstClass().method2()
        OnlineInstrumentSecondClass().method1()
    }

    @Test
    fun `check that online checker fails fast if traces do not conform`() {
        val sessionId = UUID.randomUUID()
        val cset = defineCorrelation()
                .add(correlation(onlineFirstClass.onMethod("method1", OnlineInstrumentFirstClass::method1),
                        "sid", { sessionId })
                        .extendFromInput("sid") { sessionId }.done())
                .add(correlation(onlineFirstClass.onMethod("method2", OnlineInstrumentFirstClass::method2),
                        "sid", { sessionId })
                        .noExtensions())
                .add(correlation(onlineSecondClass.onMethod("method1", OnlineInstrumentSecondClass::method1),
                        "sid", { sessionId })
                        .noExtensions())
                .finish()
        val choreography = Choreography.builder()
                .interaction(external, onlineFirstClass.onMethod("method1"), "#1")
                .interaction(onlineFirstClass, onlineFirstClass.onMethod("method2"), "#2")
                .interaction(onlineFirstClass, onlineSecondClass.onMethod("method1"), "#3")
                .returnFrom(onlineSecondClass.onMethod("method1"), "return #3")
                .end().setCorrelation(cset)
        val checker = InterceptingTestChecker(OnlineCheckerFactory.createOnlineChecker(listOf(choreography)))
        val sender = TestSender(checker)
        InterceptionConfiguration.setupInterception(sender)

        OnlineInstrumentFirstClass().method1()
        assertFailsWith(ChoreographyRuntimeException::class) { OnlineInstrumentSecondClass().method1() } // Out of order execution
        assertFailsWith(ChoreographyRuntimeException::class) { OnlineInstrumentFirstClass().method2() } // Now the entire choreography is failing
    }

    @Test
    fun `check that concurrently running choreographies work`() {
        val sessionId1 = UUID.randomUUID()
        val sessionId2 = UUID.randomUUID()
        val cset1 = defineCorrelation()
                .add(correlation(onlineFirstClass.onMethod("method1", OnlineInstrumentFirstClass::method1),
                        "sid", { sessionId1 })
                        .extendFromInput("sid") { sessionId1 }.done())
                .add(correlation(onlineFirstClass.onMethod("method2", OnlineInstrumentFirstClass::method2),
                        "sid", { sessionId1 })
                        .noExtensions())
                .add(correlation(onlineSecondClass.onMethod("method1", OnlineInstrumentSecondClass::method1),
                        "sid", { sessionId1 })
                        .noExtensions())
                .finish()
        val cset2 = defineCorrelation()
                .add(correlation(onlineSecondClass.onMethod("method2", OnlineInstrumentSecondClass::method2),
                        "sid", { sessionId2 })
                        .extendFromInput("sid") { sessionId2 }.done())
                .add(correlation(onlineThirdClass.onMethod("method1", OnlineInstrumentThirdClass::method1),
                        "sid", { sessionId2 })
                        .noExtensions())
                .add(correlation(onlineThirdClass.onMethod("method2", OnlineInstrumentThirdClass::method2),
                        "sid", { sessionId2 })
                        .noExtensions())
                .finish()
        val choreography1 = Choreography.builder()
                .interaction(external, onlineFirstClass.onMethod("method1"), "#1")
                .interaction(onlineFirstClass, onlineFirstClass.onMethod("method2"), "#2")
                .interaction(onlineFirstClass, onlineSecondClass.onMethod("method1"), "#3")
                .returnFrom(onlineSecondClass.onMethod("method1"), "return #3")
                .end().setCorrelation(cset1)

        val choreography2 = Choreography.builder()
                .interaction(external, onlineSecondClass.onMethod("method2"), "#1")
                .interaction(onlineSecondClass, onlineThirdClass.onMethod("method1"), "#2")
                .interaction(onlineThirdClass, onlineThirdClass.onMethod("method2"), "#3")
                .end().setCorrelation(cset2)

        val checker = InterceptingTestChecker(OnlineCheckerFactory.createOnlineChecker(listOf(choreography1, choreography2)))
        val sender = TestSender(checker)
        InterceptionConfiguration.setupInterception(sender)

        val thread1 = GlobalScope.async {
            OnlineInstrumentFirstClass().method1()
            OnlineInstrumentFirstClass().method2()
            OnlineInstrumentSecondClass().method1()
        }

        val thread2 = GlobalScope.async {
            OnlineInstrumentSecondClass().method2()
            OnlineInstrumentThirdClass().method1()
            OnlineInstrumentThirdClass().method2()
        }

        runBlocking { awaitAll(thread1, thread2) }
    }

    @Test
    fun `check that concurrently running choreographies work and throw exception with wrong execution`() {
        val sessionId1 = UUID.randomUUID()
        val sessionId2 = UUID.randomUUID()
        val cset1 = defineCorrelation()
                .add(correlation(onlineFirstClass.onMethod("method1", OnlineInstrumentFirstClass::method1),
                        "sid", { sessionId1 })
                        .extendFromInput("sid") { sessionId1 }.done())
                .add(correlation(onlineFirstClass.onMethod("method2", OnlineInstrumentFirstClass::method2),
                        "sid", { sessionId1 })
                        .noExtensions())
                .add(correlation(onlineSecondClass.onMethod("method1", OnlineInstrumentSecondClass::method1),
                        "sid", { sessionId1 })
                        .noExtensions())
                .finish()
        val cset2 = defineCorrelation()
                .add(correlation(onlineSecondClass.onMethod("method2", OnlineInstrumentSecondClass::method2),
                        "sid", { sessionId2 })
                        .extendFromInput("sid") { sessionId2 }.done())
                .add(correlation(onlineThirdClass.onMethod("method1", OnlineInstrumentThirdClass::method1),
                        "sid", { sessionId2 })
                        .noExtensions())
                .add(correlation(onlineThirdClass.onMethod("method2", OnlineInstrumentThirdClass::method2),
                        "sid", { sessionId2 })
                        .noExtensions())
                .finish()

        val choreography1 = Choreography.builder()
                .interaction(external, onlineFirstClass.onMethod("method1"), "#1")
                .interaction(onlineFirstClass, onlineFirstClass.onMethod("method2"), "#2")
                .interaction(onlineFirstClass, onlineSecondClass.onMethod("method1"), "#3")
                .returnFrom(onlineSecondClass.onMethod("method1"), "return #3")
                .end().setCorrelation(cset1)

        val choreography2 = Choreography.builder()
                .interaction(external, onlineSecondClass.onMethod("method2"), "#1")
                .interaction(onlineSecondClass, onlineThirdClass.onMethod("method1"), "#2")
                .interaction(onlineThirdClass, onlineThirdClass.onMethod("method2"), "#3")
                .end().setCorrelation(cset2)

        val checker = InterceptingTestChecker(OnlineCheckerFactory.createOnlineChecker(listOf(choreography1, choreography2)))
        val sender = TestSender(checker)
        InterceptionConfiguration.setupInterception(sender)

        val thread1 = GlobalScope.async {
            OnlineInstrumentFirstClass().method1()
            OnlineInstrumentSecondClass().method1() // Wrong execution order
            OnlineInstrumentFirstClass().method2()
        }

        val thread2 = GlobalScope.async {
            OnlineInstrumentSecondClass().method2()
            OnlineInstrumentThirdClass().method1()
            OnlineInstrumentThirdClass().method2()
        }

        val atomicBoolean = AtomicBoolean()
        atomicBoolean.set(false)
        runBlocking {
            try {
                thread1.await()
            } catch (e: ChoreographyRuntimeException) {
                atomicBoolean.set(true)
            }
            thread2.await()
        }
        assertTrue(atomicBoolean.get(), "Expected exception of type ${ChoreographyRuntimeException::class}")
    }

    class InterceptingTestChecker(private val checker : ChoreographyChecker) : ChoreographyChecker {
        override fun check(trace: TraceElement): CheckResult {
            val result = checker.check(trace)
            if (result == CheckResult.None) throw ChoreographyRuntimeException("NO MATCH FOUND")
            else return result
        }

        override fun check(traceDTO: InvocationDTO): CheckResult {
            val result = checker.check(traceDTO)
            if (result == CheckResult.None) throw ChoreographyRuntimeException("NO MATCH FOUND")
            else return result
        }

        override fun check(traceDTO: ReturnDTO): CheckResult {
            val result = checker.check(traceDTO)
            if (result == CheckResult.None) throw ChoreographyRuntimeException("NO MATCH FOUND")
            else return result
        }

    }

    class TestSender(private val checker: ChoreographyChecker) : TraceSender {
        override fun send(invocationDTO: InvocationDTO) {
            checker.check(invocationDTO)
        }

        override fun send(returnDTO: ReturnDTO) {
            checker.check(returnDTO)
        }

    }
}