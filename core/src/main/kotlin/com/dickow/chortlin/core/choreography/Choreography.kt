package com.dickow.chortlin.core.choreography

import com.dickow.chortlin.core.ast.ASTVisitor
import com.dickow.chortlin.core.ast.types.ASTNode
import com.dickow.chortlin.core.ast.types.placeholder.Marker
import com.dickow.chortlin.core.choreography.participant.ObservableParticipant
import com.dickow.chortlin.core.correlation.Correlation
import com.dickow.chortlin.core.correlation.CorrelationSet

data class Choreography(val start: ASTNode) {
    private lateinit var correlationSet: CorrelationSet
    companion object Instance {
        fun builder(): ChoreographyBuilder {
            return Marker()
        }
    }

    fun runVisitor(visitor: ASTVisitor): Choreography {
        start.accept(visitor)
        return this
    }

    fun setCorrelationSet(cset: CorrelationSet): Choreography {
        this.correlationSet = cset
        return this
    }

    fun getCorrelation(participant: ObservableParticipant<*>): Correlation? {
        return this.correlationSet.get(participant)
    }
}