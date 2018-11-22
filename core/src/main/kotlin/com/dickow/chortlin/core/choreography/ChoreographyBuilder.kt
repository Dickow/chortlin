package com.dickow.chortlin.core.choreography

import com.dickow.chortlin.core.choreography.participant.NonObservableParticipant
import com.dickow.chortlin.core.choreography.participant.ObservableParticipant

interface ChoreographyBuilder {
    fun <T> returnFrom(receiver: ObservableParticipant<T>, label: String): ChoreographyBuilder
    fun <T> interaction(sender: NonObservableParticipant, receiver: ObservableParticipant<T>, label: String): ChoreographyBuilder
    fun parallel(path: (ChoreographyBuilder) -> Choreography): ChoreographyBuilder
    fun choice(vararg possiblePaths: (ChoreographyBuilder) -> Choreography): Choreography
    fun end(): Choreography
}