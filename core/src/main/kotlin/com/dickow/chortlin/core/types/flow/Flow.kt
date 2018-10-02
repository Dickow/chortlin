package com.dickow.chortlin.core.types.flow

import com.dickow.chortlin.core.types.choreography.Choreography
import com.dickow.chortlin.core.types.choreography.FlowChoreography

interface Flow {
    fun finish(): Choreography {
        return FlowChoreography(this)
    }
}