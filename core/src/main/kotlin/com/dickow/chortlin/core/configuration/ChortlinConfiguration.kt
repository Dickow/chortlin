package com.dickow.chortlin.core.configuration

import com.dickow.chortlin.core.api.endpoint.Endpoint

interface ChortlinConfiguration {
    fun getEndpoint(): Endpoint
    fun applyTo(args: Array<Any?>)
    fun applyTo()
}