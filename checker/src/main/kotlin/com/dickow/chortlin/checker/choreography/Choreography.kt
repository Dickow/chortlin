package com.dickow.chortlin.checker.choreography

import com.dickow.chortlin.checker.ast.ASTBuilder
import com.dickow.chortlin.checker.ast.ASTVisitor
import com.dickow.chortlin.checker.ast.types.ASTNode
import com.dickow.chortlin.checker.ast.types.placeholder.Marker
import com.dickow.chortlin.checker.correlation.Correlation
import com.dickow.chortlin.checker.correlation.CorrelationParticipantMapping
import com.dickow.chortlin.shared.observation.Observable

data class Choreography(val start: ASTNode) {
    private lateinit var correlationParticipantMapping: CorrelationParticipantMapping

    companion object {
        @JvmStatic
        fun builder(): ASTBuilder {
            return Marker()
        }
    }

    fun runVisitor(visitor: ASTVisitor): Choreography {
        start.accept(visitor)
        return this
    }

    fun setCorrelation(cdef: CorrelationParticipantMapping): Choreography {
        this.correlationParticipantMapping = cdef
        return this
    }

    fun getCorrelation(participant: Observable): Correlation? {
        return this.correlationParticipantMapping.get(participant)
    }
}