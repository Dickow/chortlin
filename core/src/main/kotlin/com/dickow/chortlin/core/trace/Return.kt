package com.dickow.chortlin.core.trace

import com.dickow.chortlin.core.choreography.participant.ObservableParticipant

data class Return(private val participant: ObservableParticipant<*>) : TraceElement() {
    override fun getParticipant(): ObservableParticipant<*> {
        return participant
    }
}