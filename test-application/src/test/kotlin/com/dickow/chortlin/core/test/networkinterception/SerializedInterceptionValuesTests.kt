package com.dickow.chortlin.core.test.networkinterception

import com.dickow.chortlin.core.test.shared.AuthResult
import com.dickow.chortlin.core.test.shared.AuthenticatedService
import com.dickow.chortlin.core.test.shared.Authentication
import com.dickow.chortlin.core.test.shared.objects.Receipt
import com.dickow.chortlin.interception.defaults.DefaultIntercept
import com.dickow.chortlin.interception.sending.TraceSender
import com.dickow.chortlin.shared.observation.ObservableFactory
import com.dickow.chortlin.shared.trace.Invocation
import com.dickow.chortlin.shared.trace.Return
import com.dickow.chortlin.shared.trace.dto.InvocationDTO
import com.dickow.chortlin.shared.trace.dto.ReturnDTO
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

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
        val trace = Invocation(observed, arrayOf("jeppeDickow", "password"))
        sender.invocationDTOCallback = {invocationDTO ->
            assertEquals(trace.getObservation().clazz.canonicalName, invocationDTO.classCanonicalName)
            assertEquals(trace.getObservation().method.name, invocationDTO.methodName)
            assertEquals(trace.getArguments()[0], invocationDTO.arguments[0].value!!.replace("\"", ""))
            assertEquals(trace.getArguments()[1], invocationDTO.arguments[1].value!!.replace("\"", ""))
        }
        interceptor.intercept(trace)
    }

    @Test
    fun `send invocation with dto object as input`(){
        val observed = ObservableFactory.observed(AuthenticatedService::class.java, "sellItem")
        val trace = Invocation(observed, arrayOf("Coffee machine", 900, AuthResult("999")))
        interceptor.intercept(trace)
    }

    @Test
    fun `send return with dto as output object`(){
        val observed = ObservableFactory.observed(AuthenticatedService::class.java, "sellItem")
        val trace = Return(
                observed,
                arrayOf("Coffee machine", 900, AuthResult("999")),
                Receipt(1, 900, "Coffee Machine", "999"))
        interceptor.intercept(trace)
    }

    class TestSender : TraceSender {
        var invocationDTOCallback : (InvocationDTO) -> Unit = {}
        var returnDTOCallback : (ReturnDTO) -> Unit = {}

        override fun send(invocationDTO: InvocationDTO) {
            invocationDTOCallback(invocationDTO)
        }

        override fun send(returnDTO: ReturnDTO) {
            returnDTOCallback(returnDTO)
        }
    }
}