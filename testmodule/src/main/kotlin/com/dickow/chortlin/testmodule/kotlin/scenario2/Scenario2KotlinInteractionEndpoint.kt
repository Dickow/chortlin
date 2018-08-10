package com.dickow.chortlin.testmodule.kotlin.scenario2

import com.dickow.chortlin.aspect.annotation.ChortlinEndpoint
import com.dickow.chortlin.core.message.Message
import kotlin.test.fail

class Scenario2KotlinInteractionEndpoint {

    @Suppress("UNUSED_PARAMETER")
    @ChortlinEndpoint
    fun revokeRights(id: Message<Int>): Boolean {
        fail("This endpoint was not intercepted")
    }
}