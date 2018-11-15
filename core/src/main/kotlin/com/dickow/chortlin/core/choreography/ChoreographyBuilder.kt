package com.dickow.chortlin.core.choreography

import com.dickow.chortlin.core.choreography.participant.Participant

interface ChoreographyBuilder {
    fun <C> foundMessage(receiver: Participant<C>, label: String): ChoreographyBuilder
    fun <C> returnFrom(receiver: Participant<C>, label: String): ChoreographyBuilder
    fun <C1, C2> interaction(sender: Participant<C1>, receiver: Participant<C2>, label: String): ChoreographyBuilder
    fun parallel(path: (ChoreographyBuilder) -> Choreography): ChoreographyBuilder
    fun choice(vararg possiblePaths: (ChoreographyBuilder) -> Choreography): Choreography
    fun end(): Choreography
}