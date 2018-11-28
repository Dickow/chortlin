package com.dickow.chortlin.core.test.correlation

import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.external
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.correlation.CorrelationFactory
import com.dickow.chortlin.core.correlation.CorrelationFactory.fromInput
import com.dickow.chortlin.core.correlation.CorrelationFactory.fromReturn
import com.dickow.chortlin.core.correlation.CorrelationSet
import com.dickow.chortlin.core.test.shared.AuthResult
import com.dickow.chortlin.core.test.shared.AuthenticatedService
import com.dickow.chortlin.core.test.shared.Authentication
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CreateCorrelationSetsTest {

    private val client = external("Client")
    private val auth = participant(Authentication::class.java, "authenticate")
    private val buyService = participant(AuthenticatedService::class.java, "buyItem")
    private val sellService = participant(AuthenticatedService::class.java, "sellItem")

    private val authCorrelation = { username: String, _: String -> username }
    private val authExtendCorrelation = { authResult: AuthResult -> authResult.userId }
    private val buyerCorrelation = { _: String, authResult: AuthResult -> authResult.userId }

    private val cset = CorrelationSet(
            CorrelationFactory.correlation(auth, authCorrelation, fromInput(authCorrelation), fromReturn(authExtendCorrelation)),
            CorrelationFactory.correlation(buyService, buyerCorrelation)
    )

    @Test
    fun `create correlation set for small choreography`() {
        val choreography = Choreography.builder()
                .interaction(client, auth, "Authenticate client")
                .returnFrom(auth, "Client is authenticated")
                .interaction(client, buyService, "Buy the item")
                .returnFrom(buyService, "Successful buy")
                .end()
                .correlationSet(cset)

        // Try to apply the correlation function to an invocation
        val key = cset.get(auth)?.retrieveKey(arrayOf("jeppedickow", "1234!"))
        assertEquals("jeppedickow", key)
    }
}