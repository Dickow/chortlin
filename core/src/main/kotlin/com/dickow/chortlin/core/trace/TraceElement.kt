package com.dickow.chortlin.core.trace

import com.dickow.chortlin.core.choreography.participant.Participant

abstract class TraceElement<C>(private val participant: Participant<C>)