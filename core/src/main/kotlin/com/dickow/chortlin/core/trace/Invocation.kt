package com.dickow.chortlin.core.trace

import com.dickow.chortlin.core.choreography.participant.Participant

data class Invocation(private val participant: Participant<*>) : TraceElement()