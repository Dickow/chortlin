package com.dickow.chortlin.test.application.checker

import com.dickow.chortlin.checker.ast.types.factory.TypeFactory.interaction
import com.dickow.chortlin.checker.checker.factory.OnlineCheckerFactory
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.external
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.checker.correlation.factory.CorrelationFactory.correlation
import com.dickow.chortlin.checker.correlation.factory.CorrelationFactory.defineCorrelation
import com.dickow.chortlin.checker.correlation.factory.PathBuilderFactory.node
import com.dickow.chortlin.interception.configuration.InterceptionConfiguration
import com.dickow.chortlin.shared.trace.protobuf.DtoDefinitions
import com.dickow.chortlin.test.application.shared.Auth
import com.dickow.chortlin.test.application.shared.FacebookAuth
import com.dickow.chortlin.test.application.shared.GoogleAuth
import com.dickow.chortlin.test.application.shared.TestSender
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class OnlineCheckerTestsForChoice {
    private val authService = Auth()
    private val testSender: TestSender

    private val client = external("client")
    private val auth = participant(authService.javaClass.canonicalName)
    private val google = participant(GoogleAuth::class.java)
    private val facebook = participant(FacebookAuth::class.java.canonicalName)

    val cset = defineCorrelation()
            .add(correlation(auth.onMethod("authenticate"), "username", node("username").build())
                    .extendFromInput("username", node("username").build())
                    .done())
            .add(correlation(google.onMethod("login"), "username", node("identifier").build())
                    .noExtensions())
            .add(correlation(facebook.onMethod("authenticate"), "username", node("email").build())
                    .noExtensions())
            .finish()

    val choreography =
            interaction(client, auth.onMethod("authenticate"), "Client requests authentication")
                    .choice(interaction(auth, google.onMethod("login"), "Authenticate with google")
                            .returnFrom(google.onMethod("login"), "Return from google auth")
                            .returnFrom(auth.onMethod("authenticate"), "Return from authentication")
                            .end(),
                            interaction(auth, facebook.onMethod("authenticate"), "Authenticate with facebook")
                                    .returnFrom(facebook.onMethod("authenticate"), "Return from facebook auth")
                                    .returnFrom(auth.onMethod("authenticate"), "Return from authentication")
                                    .end())
                    .setCorrelation(cset)

    val checker = OnlineCheckerFactory.createOnlineChecker(listOf(choreography))

    init {
        testSender = TestSender(checker)
        InterceptionConfiguration.setupCustomInterception(testSender)
    }

    @Test
    fun `assume success when authenticate user with google`() {
        val observedTrace = LinkedList<Any>()
        testSender.sendReturnCallback = { event -> observedTrace.add(event) }
        testSender.sendInvocationCallback = { event -> observedTrace.add(event) }

        authService.authenticate("test@gmail.com", "takeMyPassword1!")
        assertEquals(4, observedTrace.size)
        assertTrue(observedTrace[0] is DtoDefinitions.InvocationDTO)
        assertTrue(observedTrace[1] is DtoDefinitions.InvocationDTO)
        assertTrue(observedTrace[2] is DtoDefinitions.ReturnDTO)
        assertTrue(observedTrace[3] is DtoDefinitions.ReturnDTO)

        val authInvocation = observedTrace[0] as DtoDefinitions.InvocationDTO
        val googleInvocation = observedTrace[1] as DtoDefinitions.InvocationDTO

        assertEquals(authService.javaClass.canonicalName, authInvocation.observed.participant)
        assertEquals("authenticate", authInvocation.observed.method)

        assertEquals(GoogleAuth::class.java.canonicalName, googleInvocation.observed.participant)
        assertEquals("login", googleInvocation.observed.method)
    }

    @Test
    fun `assume success when authenticate user with facebook`() {
        val observedTrace = LinkedList<Any>()
        testSender.sendReturnCallback = { event -> observedTrace.add(event) }
        testSender.sendInvocationCallback = { event -> observedTrace.add(event) }

        authService.authenticate("test@facebook.com", "takeMyPassword1!")
        assertEquals(4, observedTrace.size)
        assertTrue(observedTrace[0] is DtoDefinitions.InvocationDTO)
        assertTrue(observedTrace[1] is DtoDefinitions.InvocationDTO)
        assertTrue(observedTrace[2] is DtoDefinitions.ReturnDTO)
        assertTrue(observedTrace[3] is DtoDefinitions.ReturnDTO)

        val authInvocation = observedTrace[0] as DtoDefinitions.InvocationDTO
        val facebookInvocation = observedTrace[1] as DtoDefinitions.InvocationDTO

        assertEquals(authService.javaClass.canonicalName, authInvocation.observed.participant)
        assertEquals("authenticate", authInvocation.observed.method)

        assertEquals(FacebookAuth::class.java.canonicalName, facebookInvocation.observed.participant)
        assertEquals("authenticate", facebookInvocation.observed.method)
    }

}