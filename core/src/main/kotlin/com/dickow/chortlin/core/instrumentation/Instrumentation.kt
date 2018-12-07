package com.dickow.chortlin.core.instrumentation

import com.dickow.chortlin.core.choreography.participant.observation.ObservableParticipant

interface Instrumentation {
    fun before(participant: ObservableParticipant)
    fun after(participant: ObservableParticipant)
}