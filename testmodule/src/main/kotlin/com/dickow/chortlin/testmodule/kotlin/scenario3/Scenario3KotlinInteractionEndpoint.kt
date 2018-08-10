package com.dickow.chortlin.testmodule.kotlin.scenario3

import com.dickow.chortlin.aspect.annotation.ChortlinEndpoint
import com.dickow.chortlin.core.message.Message
import kotlin.test.fail

class Scenario3KotlinInteractionEndpoint {

    @Suppress("UNUSED_PARAMETER")
    @ChortlinEndpoint
    fun deleteUser(id: Message<Int>): Boolean {
        fail("This interaction endpoint was not intercepted")
    }
}