package com.dickow.chortlin.core.instrumentation

import com.dickow.chortlin.core.choreography.participant.Participant

interface Instrumentation {
    fun before(participant: Participant)
    fun after(participant: Participant)
}