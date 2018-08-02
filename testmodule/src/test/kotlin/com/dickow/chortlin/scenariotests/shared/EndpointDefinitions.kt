package com.dickow.chortlin.scenariotests.shared

import com.dickow.chortlin.aspect.annotation.ChortlinEndpoint
import kotlin.test.fail

class EndpointDefinitions {

    @ChortlinEndpoint
    fun endpointWithStringInput(str: String): Int {
        fail("This is an endpoint and must not be called")
    }

    @ChortlinEndpoint
    fun endpointWithMapInput(map: Map<String, Any>): String {
        fail("This is an endpoint and must not be called")
    }
}