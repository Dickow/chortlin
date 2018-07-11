package com.dickow.chortlin.core.test.interaction

import com.dickow.chortlin.core.Chortlin
import com.dickow.chortlin.core.message.IMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class ProxyControlTest {

    @Test
    fun `call endpoint and expect no invocation`() {
        val inputString = "Hello World"
        Chortlin.choreography()
                .onTrigger(TestEndpoint::receive)
                .mapInputTo { obj: String -> obj }
                .processWith { s ->
                    assertEquals(inputString, s)
                    Message()
                }
                .end()

        TestEndpoint().receive(inputString)
    }

    private class Message : IMessage

    private class TestEndpoint {
        fun receive(obj: String) {
            fail("This method was expected to be proxied")
        }
    }
}