package com.dickow.chortlin.scenariotests.shared

import com.dickow.chortlin.aspect.annotation.ChortlinEndpoint
import kotlin.test.fail

class InteractionDefinitions {

    @ChortlinEndpoint
    fun interaction(input: String): Int {
        fail("This is an endpoint and must not be called")
    }

    @ChortlinEndpoint
    fun interaction2(i1: String, i2: String): Int {
        fail("This is an endpoint and must not be called")
    }

    @ChortlinEndpoint
    fun interactionInteger(input: Int): Int {
        fail("This is an endpoint and must not be called")
    }
}