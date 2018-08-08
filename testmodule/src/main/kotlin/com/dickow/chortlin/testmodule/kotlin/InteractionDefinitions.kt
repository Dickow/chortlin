package com.dickow.chortlin.testmodule.kotlin

import com.dickow.chortlin.aspect.annotation.ChortlinEndpoint
import kotlin.test.fail

open class InteractionDefinitions {

    @ChortlinEndpoint
    open fun interaction(input: String): Int {
        fail("This is an endpoint and must not be called")
    }

    @ChortlinEndpoint
    open fun interaction2(i1: String, i2: String): Int {
        fail("This is an endpoint and must not be called")
    }

    @ChortlinEndpoint
    open fun interactionInteger(input: Int): Int {
        fail("This is an endpoint and must not be called")
    }
}