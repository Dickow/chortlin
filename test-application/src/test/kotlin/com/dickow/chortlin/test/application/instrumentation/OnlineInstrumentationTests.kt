package com.dickow.chortlin.test.application.instrumentation

import com.dickow.chortlin.checker.checker.ChoreographyChecker
import com.dickow.chortlin.checker.checker.factory.OnlineCheckerFactory
import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.external
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.checker.correlation.factory.CorrelationFactory.correlation
import com.dickow.chortlin.checker.correlation.factory.CorrelationFactory.defineCorrelation
import com.dickow.chortlin.checker.correlation.factory.PathBuilderFactory.root
import com.dickow.chortlin.interception.configuration.InterceptionConfiguration
import com.dickow.chortlin.interception.sending.TraceSender
import com.dickow.chortlin.shared.exceptions.ChoreographyRuntimeException
import com.dickow.chortlin.shared.trace.protobuf.DtoDefinitions
import com.dickow.chortlin.test.application.shared.OnlineInstrumentFirstClass
import com.dickow.chortlin.test.application.shared.OnlineInstrumentSecondClass
import com.dickow.chortlin.test.application.shared.OnlineInstrumentThirdClass
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
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
        val cset = defineCorrelation()
                .add(correlation(onlineFirstClass.onMethod("method1"),
                        "sid", root().build())
                        .extendFromInput("sid", root().build()).done())
                .add(correlation(onlineFirstClass.onMethod("method2"),
                        "sid", root().build())
                        .noExtensions())
                .add(correlation(onlineSecondClass.onMethod("method1"),
                        "sid", root().build())
                        .noExtensions())
                .finish()
        val choreography = Choreography.builder()
                .interaction(external, onlineFirstClass.onMethod("method1"), "#1")
                .interaction(onlineFirstClass, onlineFirstClass.onMethod("method2"), "#2")
                .interaction(onlineFirstClass, onlineSecondClass.onMethod("method1"), "#3")
                .returnFrom(onlineSecondClass.onMethod("method1"), "return #3")
                .end().setCorrelation(cset)
        val checker = OnlineCheckerFactory.createOnlineChecker(listOf(choreography))
        val sender = TestSender(checker)
        InterceptionConfiguration.setupCustomInterception(sender)

        OnlineInstrumentFirstClass().method1()
        OnlineInstrumentFirstClass().method2()
        OnlineInstrumentSecondClass().method1()
    }

    @Test
    fun `check that online checker fails fast if traces do not conform`() {
        val cset = defineCorrelation()
                .add(correlation(onlineFirstClass.onMethod("method1"),
                        "sid", root().build())
                        .extendFromInput("sid", root().build()).done())
                .add(correlation(onlineFirstClass.onMethod("method2"),
                        "sid", root().build())
                        .noExtensions())
                .add(correlation(onlineSecondClass.onMethod("method1"),
                        "sid", root().build())
                        .noExtensions())
                .finish()
        val choreography = Choreography.builder()
                .interaction(external, onlineFirstClass.onMethod("method1"), "#1")
                .interaction(onlineFirstClass, onlineFirstClass.onMethod("method2"), "#2")
                .interaction(onlineFirstClass, onlineSecondClass.onMethod("method1"), "#3")
                .returnFrom(onlineSecondClass.onMethod("method1"), "return #3")
                .end().setCorrelation(cset)
        val checker = OnlineCheckerFactory.createOnlineChecker(listOf(choreography))
        val sender = TestSender(checker)
        InterceptionConfiguration.setupCustomInterception(sender)

        OnlineInstrumentFirstClass().method1()
        assertFailsWith(ChoreographyRuntimeException::class) { OnlineInstrumentSecondClass().method1() } // Out of order execution
        assertFailsWith(ChoreographyRuntimeException::class) { OnlineInstrumentFirstClass().method2() } // Now the entire choreography is failing
    }

    @Test
    fun `check that concurrently running choreographies work`() {
        val cset1 = defineCorrelation()
                .add(correlation(onlineFirstClass.onMethod("method1"),
                        "sid", root().build())
                        .extendFromInput("sid", root().build()).done())
                .add(correlation(onlineFirstClass.onMethod("method2"),
                        "sid", root().build())
                        .noExtensions())
                .add(correlation(onlineSecondClass.onMethod("method1"),
                        "sid", root().build())
                        .noExtensions())
                .finish()
        val cset2 = defineCorrelation()
                .add(correlation(onlineSecondClass.onMethod("method2"),
                        "sid", root().build())
                        .extendFromInput("sid", root().build()).done())
                .add(correlation(onlineThirdClass.onMethod("method1"),
                        "sid", root().build())
                        .noExtensions())
                .add(correlation(onlineThirdClass.onMethod("method2"),
                        "sid", root().build())
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

        val checker = OnlineCheckerFactory.createOnlineChecker(listOf(choreography1, choreography2))
        val sender = TestSender(checker)
        InterceptionConfiguration.setupCustomInterception(sender)

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
        val cset1 = defineCorrelation()
                .add(correlation(onlineFirstClass.onMethod("method1"),
                        "sid", root().build())
                        .extendFromInput("sid", root().build()).done())
                .add(correlation(onlineFirstClass.onMethod("method2"),
                        "sid", root().build())
                        .noExtensions())
                .add(correlation(onlineSecondClass.onMethod("method1"),
                        "sid", root().build())
                        .noExtensions())
                .finish()
        val cset2 = defineCorrelation()
                .add(correlation(onlineSecondClass.onMethod("method2"),
                        "sid", root().build())
                        .extendFromInput("sid", root().build()).done())
                .add(correlation(onlineThirdClass.onMethod("method1"),
                        "sid", root().build())
                        .noExtensions())
                .add(correlation(onlineThirdClass.onMethod("method2"),
                        "sid", root().build())
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

        val checker = OnlineCheckerFactory.createOnlineChecker(listOf(choreography1, choreography2))
        val sender = TestSender(checker)
        InterceptionConfiguration.setupCustomInterception(sender)

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

    class TestSender(private val checker: ChoreographyChecker) : TraceSender {
        override fun send(invocationDTO: DtoDefinitions.InvocationDTO) {
            checker.check(invocationDTO)
        }

        override fun send(returnDTO: DtoDefinitions.ReturnDTO) {
            checker.check(returnDTO)
        }

    }
}