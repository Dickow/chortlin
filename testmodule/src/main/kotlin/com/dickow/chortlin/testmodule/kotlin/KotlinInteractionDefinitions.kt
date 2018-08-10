package com.dickow.chortlin.testmodule.kotlin

import com.dickow.chortlin.aspect.annotation.ChortlinEndpoint
import com.dickow.chortlin.core.message.Message
import kotlin.test.fail

open class KotlinInteractionDefinitions {

    @ChortlinEndpoint
    open fun interaction(input: Message<String>): Int {
        fail("This is an endpoint and must not be called")
    }

    @ChortlinEndpoint
    open fun interactionInteger(input: Message<Int>): Int {
        fail("This is an endpoint and must not be called")
    }
}