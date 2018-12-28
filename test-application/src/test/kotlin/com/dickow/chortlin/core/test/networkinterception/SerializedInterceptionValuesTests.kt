package com.dickow.chortlin.core.test.networkinterception

import com.dickow.chortlin.core.test.shared.AuthResult
import com.dickow.chortlin.core.test.shared.AuthenticatedService
import com.dickow.chortlin.core.test.shared.Authentication
import com.dickow.chortlin.core.test.shared.builder.TestObservableBuilder.buildInvocation
import com.dickow.chortlin.core.test.shared.objects.Receipt
import com.dickow.chortlin.interception.defaults.DefaultIntercept
import com.dickow.chortlin.interception.sending.TraceSender
import com.dickow.chortlin.shared.observation.ObservableFactory
import com.dickow.chortlin.shared.trace.protobuf.DtoDefinitions
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SerializedInterceptionValuesTests {
    private val sender = TestSender()
    private var interceptor = DefaultIntercept(sender)

    @BeforeEach
    internal fun setUp() {
        sender.returnDTOCallback = {}
        sender.invocationDTOCallback = {}
    }

    @Test
    fun `observe simple trace and send it across the serialisation`(){
        val observed = ObservableFactory.observed(Authentication::class.java, "authenticate")
        val trace = buildInvocation(observed, arrayOf("jeppeDickow", "password"))
        sender.invocationDTOCallback = {invocationDTO ->
            assertEquals(trace.getObservation().clazz.canonicalName, invocationDTO.observed.participant)
            assertEquals(trace.getObservation().method.name, invocationDTO.observed.method)
            assertTrue(invocationDTO.argumentTree.contains("jeppeDickow"))
            assertTrue(invocationDTO.argumentTree.contains("password"))
        }
        interceptor.interceptInvocation(observed, arrayOf("jeppeDickow", "password"))
    }

    @Test
    fun `send invocation with dto object as input`(){
        val observed = ObservableFactory.observed(AuthenticatedService::class.java, "sellItem")
        interceptor.interceptInvocation(observed, arrayOf("Coffee machine", 900, AuthResult("999")))
    }

    @Test
    fun `send return with dto as output object`(){
        val observed = ObservableFactory.observed(AuthenticatedService::class.java, "sellItem")
        interceptor.interceptReturn(observed,
                arrayOf("Coffee machine", 900, AuthResult("999")),
                Receipt(1, 900, "Coffee Machine", "999"))
    }

    class TestSender : TraceSender {
        var invocationDTOCallback: (DtoDefinitions.InvocationDTO) -> Unit = {}
        var returnDTOCallback: (DtoDefinitions.ReturnDTO) -> Unit = {}

        override fun send(invocationDTO: DtoDefinitions.InvocationDTO) {
            invocationDTOCallback(invocationDTO)
        }

        override fun send(returnDTO: DtoDefinitions.ReturnDTO) {
            returnDTOCallback(returnDTO)
        }
    }
}