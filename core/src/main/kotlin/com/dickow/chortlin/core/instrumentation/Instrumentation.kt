package com.dickow.chortlin.core.instrumentation

import com.dickow.chortlin.core.choreography.participant.Participant

interface Instrumentation {
    fun <C> around(participant: Participant<C>)
    fun <C> before(participant: Participant<C>)
    fun <C> after(participant: Participant<C>)
}