package com.dickow.chortlin.core.test.correlation

import com.dickow.chortlin.core.checker.OnlineChecker
import com.dickow.chortlin.core.checker.factory.CheckerFactory
import com.dickow.chortlin.core.checker.session.InMemorySessionManager
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.external
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.correlation.factory.CorrelationFactory.correlation
import com.dickow.chortlin.core.correlation.factory.CorrelationFactory.defineCorrelationSet
import com.dickow.chortlin.core.exceptions.ChortlinRuntimeException
import com.dickow.chortlin.core.exceptions.InvalidChoreographyException
import com.dickow.chortlin.core.instrumentation.ASTInstrumentation
import com.dickow.chortlin.core.instrumentation.ByteBuddyInstrumentation
import com.dickow.chortlin.core.instrumentation.strategy.CheckInMemory
import com.dickow.chortlin.core.instrumentation.strategy.InstrumentationStrategy
import com.dickow.chortlin.core.test.shared.AuthResult
import com.dickow.chortlin.core.test.shared.AuthenticatedService
import com.dickow.chortlin.core.test.shared.Authentication
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CreateCorrelationSetsTest {
    private val instrumentation = ASTInstrumentation(ByteBuddyInstrumentation)

    private val client = external("Client")
    private val auth = participant(Authentication::class.java, "authenticate", Authentication::authenticate)
    private val buyService = participant(AuthenticatedService::class.java, "buyItem", AuthenticatedService::buyItem)
    //private val sellService = participant(AuthenticatedService::class.java, "sellItem")

    private val authService = Authentication()
    private val itemService = AuthenticatedService()

    private val authCorrelation = { username: String, _: String -> username }
    private val authExtendCorrelation = { authResult: AuthResult -> authResult.userId }
    private val buyerCorrelation = { _: String, authResult: AuthResult -> authResult.userId }

    private val cset = defineCorrelationSet()
            .add(correlation(auth, authCorrelation)
                    .extendFromInput(authCorrelation)
                    .extendFromReturn(authExtendCorrelation)
                    .done())
            .add(correlation(buyService, buyerCorrelation).noExtensions())
            .finish()

    @Test
    fun `create correlation set for small choreography`() {
        Choreography.builder()
                .interaction(client, auth, "Authenticate client")
                .returnFrom(auth, "Client is authenticated")
                .interaction(client, buyService, "Buy the item")
                .returnFrom(buyService, "Successful buy")
                .end()
                .setCorrelationSet(cset)

        // Try to apply the correlation function to an invocation
        val key = cset.get(auth)?.retrieveKey(arrayOf("jeppedickow", "1234!"))
        assertEquals("jeppedickow", key)
    }

    @Test
    fun `expect error when creating checker for choreography with lacking correlation set`() {
        val choreography = Choreography.builder()
                .interaction(client, auth, "Authenticate client")
                .returnFrom(auth, "Client is authenticated")
                .interaction(client, buyService, "Buy the item")
                .returnFrom(buyService, "Successful buy")
                .end()
                .setCorrelationSet(defineCorrelationSet()
                        .add(correlation(auth, authCorrelation)
                                .extendFromInput(authCorrelation)
                                .extendFromReturn(authExtendCorrelation).done())
                        .finish())

        assertFailsWith(InvalidChoreographyException::class) {
            CheckerFactory.createChecker(choreography)
        }
    }

    @Test
    fun `expect success when running multiple instances of the services with correlation sets`() {
        val choreography = Choreography.builder()
                .interaction(client, auth, "Authenticate client")
                .returnFrom(auth, "Client is authenticated")
                .interaction(client, buyService, "Buy the item")
                .returnFrom(buyService, "Successful buy")
                .end()
                .setCorrelationSet(cset)
                .runVisitor(instrumentation)
        val onlineChecker = OnlineChecker(InMemorySessionManager(listOf(choreography)))
        InstrumentationStrategy.strategy = CheckInMemory(onlineChecker, true)

        val authResult1 = authService.authenticate("jeppedickow", "1234!")
        val authResult2 = authService.authenticate("lars", "4321!")
        val authResult3 = authService.authenticate("klaus", "2314!")
        itemService.buyItem("test", authResult1)
        itemService.buyItem("bucket", authResult3)
        itemService.buyItem("horse", authResult2)
    }

    @Test
    fun `expect error when executing a session in the wrong order`() {
        val choreography = Choreography.builder()
                .interaction(client, auth, "Authenticate client")
                .returnFrom(auth, "Client is authenticated")
                .interaction(client, buyService, "Buy the item")
                .returnFrom(buyService, "Successful buy")
                .end()
                .setCorrelationSet(cset)
                .runVisitor(instrumentation)
        val onlineChecker = OnlineChecker(InMemorySessionManager(listOf(choreography)))
        InstrumentationStrategy.strategy = CheckInMemory(onlineChecker, true)

        val authResult1 = authService.authenticate("jeppedickow", "1234!")
        val authResult2 = authService.authenticate("lars", "4321!")
        itemService.buyItem("test", authResult1)
        itemService.buyItem("horse", authResult2)
        assertFailsWith(ChortlinRuntimeException::class) { itemService.buyItem("bucket", AuthResult("lars")) }
    }
}