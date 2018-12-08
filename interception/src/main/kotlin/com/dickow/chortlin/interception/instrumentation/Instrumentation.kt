package com.dickow.chortlin.inteception.instrumentation

import com.dickow.chortlin.shared.observation.ObservableParticipant


interface Instrumentation {
    fun before(participant: ObservableParticipant)
    fun after(participant: ObservableParticipant)
}