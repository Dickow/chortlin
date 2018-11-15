package com.dickow.chortlin.core.trace

import com.dickow.chortlin.core.choreography.participant.Participant

abstract class TraceElement {
    abstract fun getParticipant(): Participant<*>
}