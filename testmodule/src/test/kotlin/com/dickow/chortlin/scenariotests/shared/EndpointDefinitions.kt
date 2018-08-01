package com.dickow.chortlin.scenariotests.shared

import com.dickow.chortlin.aspect.annotation.ChortlinEndpoint

class EndpointDefinitions {

    @ChortlinEndpoint
    fun endpointWithStringInput(str: String): Int {
        return str.toInt()
    }

    @ChortlinEndpoint
    fun endpointWithMapInput(map: Map<String, Any>): String {
        return map.keys.joinToString { s -> s }
    }
}