package com.dickow.chortlin.testmodule.kotlin.scenario2

import com.dickow.chortlin.aspect.annotation.ChortlinEndpoint
import kotlin.test.fail

class Scenario2KotlinTriggerEndpoint {
    @Suppress("UNUSED_PARAMETER")
    @ChortlinEndpoint
    fun reduceRightsForUser(id: Long, name: String, isAdmin: Boolean): Int {
        fail("This endpoint should have been intercepted")
    }
}