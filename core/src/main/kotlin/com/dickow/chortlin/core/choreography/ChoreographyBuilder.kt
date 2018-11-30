package com.dickow.chortlin.core.choreography

import com.dickow.chortlin.core.choreography.participant.NonObservableParticipant
import com.dickow.chortlin.core.choreography.participant.Participant

interface ChoreographyBuilder {
    fun returnFrom(receiver: Participant, label: String): ChoreographyBuilder
    fun interaction(sender: NonObservableParticipant, receiver: Participant, label: String): ChoreographyBuilder
    fun parallel(path: (ChoreographyBuilder) -> Choreography): ChoreographyBuilder
    fun choice(vararg possiblePaths: (ChoreographyBuilder) -> Choreography): Choreography
    fun end(): Choreography
}