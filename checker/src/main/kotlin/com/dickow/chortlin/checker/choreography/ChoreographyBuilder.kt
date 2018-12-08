package com.dickow.chortlin.checker.choreography

import com.dickow.chortlin.checker.choreography.method.ChortlinMethod
import com.dickow.chortlin.checker.choreography.participant.Participant

interface ChoreographyBuilder {
    fun <C> returnFrom(method: ChortlinMethod<C>, label: String): ChoreographyBuilder
    fun <C> interaction(sender: Participant, method: ChortlinMethod<C>, label: String): ChoreographyBuilder
    fun parallel(path: (ChoreographyBuilder) -> Choreography): ChoreographyBuilder
    fun choice(vararg possiblePaths: (ChoreographyBuilder) -> Choreography): Choreography
    fun end(): Choreography
}