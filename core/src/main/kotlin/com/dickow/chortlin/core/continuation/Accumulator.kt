package com.dickow.chortlin.core.continuation

import com.dickow.chortlin.core.api.endpoint.Endpoint

class Accumulator(val endpoint: Endpoint) {
    var hashes: Collection<Int> = emptyList()
}