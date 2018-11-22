package com.dickow.chortlin.core.trace

import com.dickow.chortlin.core.choreography.participant.ObservableParticipant

abstract class TraceElement {
    abstract fun getParticipant(): ObservableParticipant<*>
}