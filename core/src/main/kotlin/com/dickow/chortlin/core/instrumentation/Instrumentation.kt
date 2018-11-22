package com.dickow.chortlin.core.instrumentation

import com.dickow.chortlin.core.choreography.participant.ObservableParticipant

interface Instrumentation {
    fun <T> before(participant: ObservableParticipant<T>)
    fun <T> after(participant: ObservableParticipant<T>)
}