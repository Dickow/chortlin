package com.dickow.chortlin.testmodule.kotlin.scenario2

import com.dickow.chortlin.aspect.annotation.ChortlinEndpoint
import kotlin.test.fail

class Scenario2KotlinInteractionEndpoint {

    @Suppress("UNUSED_PARAMETER")
    @ChortlinEndpoint
    fun revokeRights(id: Int): Boolean {
        fail("This endpoint was not intercepted")
    }
}