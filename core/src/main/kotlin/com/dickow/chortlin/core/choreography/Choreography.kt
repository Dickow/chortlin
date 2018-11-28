package com.dickow.chortlin.core.choreography

import com.dickow.chortlin.core.ast.ASTVisitor
import com.dickow.chortlin.core.ast.types.ASTNode
import com.dickow.chortlin.core.ast.types.placeholder.Marker
import com.dickow.chortlin.core.ast.validation.ASTValidator
import com.dickow.chortlin.core.checker.ChoreographyChecker
import com.dickow.chortlin.core.choreography.validation.ChoreographyValidation
import com.dickow.chortlin.core.correlation.CorrelationSet

data class Choreography(val start: ASTNode) {
    private lateinit var correlationSet: CorrelationSet
    companion object Instance {
        fun builder(): ChoreographyBuilder {
            return Marker()
        }
    }

    fun createChecker(): ChoreographyChecker {
        runVisitor(ASTValidator())
        runVisitor(ChoreographyValidation(correlationSet))
        return ChoreographyChecker(this)
    }

    fun runVisitor(visitor: ASTVisitor): Choreography {
        start.accept(visitor)
        return this
    }

    fun correlationSet(cset: CorrelationSet): Choreography {
        this.correlationSet = cset
        return this
    }
}