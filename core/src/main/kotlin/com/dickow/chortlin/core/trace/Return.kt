package com.dickow.chortlin.core.trace

import com.dickow.chortlin.core.choreography.participant.Participant

data class Return(private val participant: Participant<*>) : TraceElement()