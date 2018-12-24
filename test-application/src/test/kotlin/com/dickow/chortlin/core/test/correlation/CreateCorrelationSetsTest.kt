package com.dickow.chortlin.core.test.correlation

import com.dickow.chortlin.checker.checker.factory.OnlineCheckerFactory
import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.external
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.checker.correlation.CorrelationValue
import com.dickow.chortlin.checker.correlation.factory.CorrelationFactory.correlation
import com.dickow.chortlin.checker.correlation.factory.CorrelationFactory.defineCorrelation
import com.dickow.chortlin.core.test.instrumentation.OnlineInstrumentationTests
import com.dickow.chortlin.core.test.shared.AuthResult
import com.dickow.chortlin.core.test.shared.AuthenticatedService
import com.dickow.chortlin.core.test.shared.Authentication
import com.dickow.chortlin.interception.configuration.InterceptionConfiguration
import com.dickow.chortlin.shared.exceptions.ChoreographyRuntimeException
import com.dickow.chortlin.shared.exceptions.InvalidChoreographyException
import com.dickow.chortlin.shared.observation.ObservableParticipant
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CreateCorrelationSetsTest {
    private val client = external("Client")
    private val auth = participant(Authentication::class.java) // "authenticate", Authentication::authenticate
    private val buyService = participant(AuthenticatedService::class.java) // "buyItem", AuthenticatedService::buyItem

    private val authService = Authentication()
    private val itemService = AuthenticatedService()

    private val authCorrelation = { username: String, _: String -> username }
    private val authExtendCorrelation = { authResult: AuthResult -> authResult.userId }
    private val buyerCorrelation = { _: String, authResult: AuthResult -> authResult.userId }
    private val authenticationChoreography = Choreography.builder()
            .interaction(client, auth.onMethod("authenticate"), "Authenticate client")
            .returnFrom(auth.onMethod("authenticate"), "Client is authenticated")
            .interaction(client, buyService.onMethod("buyItem"), "Buy the item")
            .returnFrom(buyService.onMethod("buyItem"), "Successful buy")
            .end()

    private val cset = defineCorrelation()
            .add(correlation(auth.onMethod("authenticate", Authentication::authenticate), "uName", authCorrelation)
                    .extendFromInput("uName", authCorrelation)
                    .extendFromReturn("userId", authExtendCorrelation)
                    .done())
            .add(correlation(buyService.onMethod("buyItem", AuthenticatedService::buyItem), "userId", buyerCorrelation)
                    .noExtensions())
            .finish()

    @Test
    fun `create correlation set for small choreography`() {
        // Try to apply the correlation function to an invocation
        val observableAuth = ObservableParticipant(auth.clazz, auth.onMethod("authenticate").jvmMethod)
        val key = cset.get(observableAuth)?.retrieveKey(arrayOf("jeppedickow", "1234!"))
        assertEquals(CorrelationValue("uName", "jeppedickow"), key)
    }

    @Test
    fun `expect error when creating checker for choreography with lacking correlation set`() {
        authenticationChoreography
                .setCorrelation(defineCorrelation()
                        .add(correlation(auth.onMethod("authenticate", Authentication::authenticate), "uName", authCorrelation)
                                .extendFromInput("uName", authCorrelation)
                                .extendFromReturn("userId", authExtendCorrelation).done())
                        .finish())

        assertFailsWith(InvalidChoreographyException::class) {
            OnlineCheckerFactory.createOnlineChecker(listOf(authenticationChoreography))
        }
    }

    @Test
    fun `expect success when running multiple instances of the services with correlation sets`() {
        authenticationChoreography.setCorrelation(cset)
        val checker = OnlineInstrumentationTests.InterceptingTestChecker(OnlineCheckerFactory.createOnlineChecker(listOf(authenticationChoreography)))
        val sender = OnlineInstrumentationTests.TestSender(checker)
        InterceptionConfiguration.setupCustomInterception(sender)

        val authResult1 = authService.authenticate("jeppedickow", "1234!")
        val authResult2 = authService.authenticate("lars", "4321!")
        val authResult3 = authService.authenticate("klaus", "2314!")
        itemService.buyItem("test", authResult1)
        itemService.buyItem("bucket", authResult3)
        itemService.buyItem("horse", authResult2)
    }

    @Test
    fun `expect error when executing a session in the wrong order`() {
        authenticationChoreography.setCorrelation(cset)
        val checker = OnlineInstrumentationTests.InterceptingTestChecker(OnlineCheckerFactory.createOnlineChecker(listOf(authenticationChoreography)))
        val sender = OnlineInstrumentationTests.TestSender(checker)
        InterceptionConfiguration.setupCustomInterception(sender)
        val authResult1 = authService.authenticate("jeppedickow", "1234!")
        val authResult2 = authService.authenticate("lars", "4321!")
        itemService.buyItem("test", authResult1)
        itemService.buyItem("horse", authResult2)
        assertFailsWith(ChoreographyRuntimeException::class) { itemService.buyItem("bucket", AuthResult("lars")) }
    }

    @Test
    fun `expect an error when correlation function uses wrong correlation key`() {
        authenticationChoreography
                .setCorrelation(defineCorrelation()
                        .add(correlation(auth.onMethod("authenticate", Authentication::authenticate), "uName", authCorrelation)
                                .extendFromInput("user", authCorrelation) // Adding the value under another identifier
                                .extendFromReturn("userId", authExtendCorrelation)
                                .done())
                        .add(correlation(buyService.onMethod("buyItem", AuthenticatedService::buyItem), "userId", buyerCorrelation)
                                .noExtensions())
                        .finish())
        val checker = OnlineInstrumentationTests.InterceptingTestChecker(OnlineCheckerFactory.createOnlineChecker(listOf(authenticationChoreography)))
        val sender = OnlineInstrumentationTests.TestSender(checker)
        InterceptionConfiguration.setupCustomInterception(sender)
        assertFailsWith(ChoreographyRuntimeException::class) { authService.authenticate("jeppedickow", "1234!") }
    }
}