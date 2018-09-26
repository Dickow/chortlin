package com.dickow.chortlin.core.api.type.start

import com.dickow.chortlin.core.types.Participant
import com.dickow.chortlin.core.types.Start

class StartTypeAPI : IStartTypeAPI {
    override fun <T> start(participant: Participant<T>): Start<T> {
        return Start(participant)
    }
}