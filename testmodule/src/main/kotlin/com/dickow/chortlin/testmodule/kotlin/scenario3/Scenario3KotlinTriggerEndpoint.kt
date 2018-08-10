package com.dickow.chortlin.testmodule.kotlin.scenario3

import com.dickow.chortlin.aspect.annotation.ChortlinEndpoint
import kotlin.test.fail

class Scenario3KotlinTriggerEndpoint {

    @Suppress("UNUSED_PARAMETER")
    @ChortlinEndpoint
    fun endpoint(id: Int, name: String): Int {
        fail("This endpoint was not intercepted")
    }
}