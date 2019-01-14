package com.dickow.chortlin.checker.ast

import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.checker.choreography.participant.ObservableParticipant
import com.dickow.chortlin.checker.choreography.participant.Participant

interface ASTBuilder {
    fun returnFrom(receiver: ObservableParticipant, label: String): ASTBuilder
    fun interaction(sender: Participant, receiver: ObservableParticipant, label: String): ASTBuilder
    fun choice(vararg branches: Choreography): Choreography
    fun end(): Choreography
}