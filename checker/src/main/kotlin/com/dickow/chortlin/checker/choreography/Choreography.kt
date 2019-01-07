package com.dickow.chortlin.checker.choreography

import com.dickow.chortlin.checker.ast.ASTBuilder
import com.dickow.chortlin.checker.ast.ASTVisitor
import com.dickow.chortlin.checker.ast.types.ASTNode
import com.dickow.chortlin.checker.ast.types.placeholder.EmptyAST
import com.dickow.chortlin.checker.checker.ParticipantRetriever
import com.dickow.chortlin.checker.correlation.Correlation
import com.dickow.chortlin.checker.correlation.CorrelationDefinition
import com.dickow.chortlin.shared.observation.Observable

data class Choreography(val start: ASTNode) {
    private lateinit var correlationDefinition: CorrelationDefinition
    private val observables: Set<Observable>

    init {
        val participantRetriever = ParticipantRetriever()
        start.accept(participantRetriever)
        observables = participantRetriever.getParticipants()
    }

    companion object {
        @JvmStatic
        fun builder(): ASTBuilder {
            return EmptyAST()
        }
    }

    fun runVisitor(visitor: ASTVisitor): Choreography {
        start.accept(visitor)
        return this
    }

    fun setCorrelation(cdef: CorrelationDefinition): Choreography {
        this.correlationDefinition = cdef
        return this
    }

    fun getCorrelation(participant: Observable): Correlation? {
        return this.correlationDefinition.get(participant)
    }

    fun contains(observable: Observable): Boolean {
        return observables.contains(observable)
    }
}