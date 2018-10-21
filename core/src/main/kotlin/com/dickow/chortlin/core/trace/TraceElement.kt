package com.dickow.chortlin.core.trace

import com.dickow.chortlin.core.choreography.participant.Participant

data class TraceElement<C>(private val participant: Participant<C>)