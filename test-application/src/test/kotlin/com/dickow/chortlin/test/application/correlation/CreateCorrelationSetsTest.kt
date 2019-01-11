package com.dickow.chortlin.test.application.correlation

import com.dickow.chortlin.checker.ast.types.factory.TypeFactory.interaction
import com.dickow.chortlin.checker.checker.factory.OnlineCheckerFactory
import com.dickow.chortlin.checker.choreography.participant.ObservableParticipant
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.external
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.checker.correlation.CorrelationValue
import com.dickow.chortlin.checker.correlation.factory.CorrelationFactory.correlation
import com.dickow.chortlin.checker.correlation.factory.CorrelationFactory.defineCorrelation
import com.dickow.chortlin.checker.correlation.factory.PathBuilderFactory.node
import com.dickow.chortlin.checker.trace.value.ObjectValue
import com.dickow.chortlin.checker.trace.value.StringValue
import com.dickow.chortlin.interception.configuration.InterceptionConfiguration
import com.dickow.chortlin.shared.exceptions.ChoreographyRuntimeException
import com.dickow.chortlin.shared.exceptions.InvalidChoreographyException
import com.dickow.chortlin.test.application.instrumentation.OnlineInstrumentationTests
import com.dickow.chortlin.test.application.shared.AuthResult
import com.dickow.chortlin.test.application.shared.AuthenticatedService
import com.dickow.chortlin.test.application.shared.Authentication
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CreateCorrelationSetsTest {
    private val client = external("Client")
    private val auth = participant(Authentication::class.java) // "authenticate", Authentication::authenticate
    private val buyService = participant(AuthenticatedService::class.java) // "buyItem", AuthenticatedService::buyItem

    private val authService = Authentication()
    private val itemService = AuthenticatedService()

    private val authCorrelation = node("arg0").build()
    private val authExtendCorrelation = node("userId").build()
    private val buyerCorrelation = node("arg1").node("userId").build()
    private val authenticationChoreography =
            interaction(client, auth.onMethod("authenticate"), "Authenticate client")
            .returnFrom(auth.onMethod("authenticate"), "Client is authenticated")
            .interaction(client, buyService.onMethod("buyItem"), "Buy the item")
            .returnFrom(buyService.onMethod("buyItem"), "Successful buy")
            .end()

    private val cset = defineCorrelation()
            .add(correlation(auth.onMethod("authenticate"), "uName", authCorrelation)
                    .extendFromInput("uName", authCorrelation)
                    .extendFromReturn("userId", authExtendCorrelation).done())
            .add(correlation(buyService.onMethod("buyItem"), "userId", buyerCorrelation)
                    .noExtensions())
            .finish()

    @Test
    fun `create correlation set for small choreography`() {
        // Try to apply the correlation function to an invocation
        val observableAuth = ObservableParticipant(auth.identifier,"authenticate")
        val arguments = ObjectValue(mapOf(Pair("arg0", StringValue("jeppedickow")), Pair("arg1", StringValue("1234!"))))
        val key = cset.get(observableAuth)?.retrieveKey(arguments)
        assertEquals(CorrelationValue("uName", StringValue("jeppedickow")), key)
    }

    @Test
    fun `expect error when creating checker for choreography with lacking correlation set`() {
        authenticationChoreography
                .setCorrelation(defineCorrelation()
                        .add(correlation(auth.onMethod("authenticate"), "uName", authCorrelation)
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
        val checker = OnlineCheckerFactory.createOnlineChecker(listOf(authenticationChoreography))
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
        val checker = OnlineCheckerFactory.createOnlineChecker(listOf(authenticationChoreography))
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
                        .add(correlation(auth.onMethod("authenticate"), "uName", authCorrelation)
                                .extendFromInput("user", authCorrelation) // Adding the value under another identifier
                                .extendFromReturn("userId", authExtendCorrelation)
                                .done())
                        .add(correlation(buyService.onMethod("buyItem"), "userId", buyerCorrelation)
                                .noExtensions())
                        .finish())
        val checker = OnlineCheckerFactory.createOnlineChecker(listOf(authenticationChoreography))
        val sender = OnlineInstrumentationTests.TestSender(checker)
        InterceptionConfiguration.setupCustomInterception(sender)
        assertFailsWith(ChoreographyRuntimeException::class) { authService.authenticate("jeppedickow", "1234!") }
    }
}