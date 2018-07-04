package com.dickow.chortlin.core

import com.dickow.chortlin.core.api.implementations.EndpointAPI
import com.dickow.chortlin.core.api.interfaces.IEndpointAPI

object Chortlin {
    fun beginChoreography(): IEndpointAPI {
        return EndpointAPI()
    }
}

