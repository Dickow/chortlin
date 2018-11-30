package com.dickow.chortlin.core.trace

import com.dickow.chortlin.core.choreography.participant.observation.ObservedParticipant

abstract class TraceElement {
    abstract fun getParticipant(): ObservedParticipant
    abstract fun getArguments(): Array<Any>
}