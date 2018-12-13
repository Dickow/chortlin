package com.dickow.chortlin.checker.ast

import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.checker.choreography.method.ChortlinMethod
import com.dickow.chortlin.checker.choreography.participant.Participant

interface ASTBuilder {
    fun <C> returnFrom(method: ChortlinMethod<C>, label: String): ASTBuilder
    fun <C> interaction(sender: Participant, method: ChortlinMethod<C>, label: String): ASTBuilder
    fun parallel(path: (ASTBuilder) -> Choreography): ASTBuilder
    fun choice(vararg possiblePaths: (ASTBuilder) -> Choreography): Choreography
    fun end(): Choreography
}