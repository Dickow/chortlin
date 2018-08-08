package com.dickow.chortlin.testmodule.kotlin

import com.dickow.chortlin.aspect.annotation.ChortlinEndpoint
import kotlin.test.fail

open class EndpointDefinitions {

    @ChortlinEndpoint
    open fun endpointWithStringInput(str: String): Int {
        fail("This is an endpoint and must not be called")
    }

    @ChortlinEndpoint
    open fun endpointWithMapInput(map: Map<String, Any>): String {
        fail("This is an endpoint and must not be called")
    }
}