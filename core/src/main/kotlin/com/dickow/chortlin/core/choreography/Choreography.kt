package com.dickow.chortlin.core.choreography

import com.dickow.chortlin.core.ast.types.ASTNode
import com.dickow.chortlin.core.ast.types.Marker
import com.dickow.chortlin.core.checker.ASTVisitor
import com.dickow.chortlin.core.checker.ChoreographyChecker
import com.dickow.chortlin.core.validation.ChoreographyValidator

data class Choreography(val start: ASTNode) {
    companion object Instance {
        fun builder(): ChoreographyBuilder {
            return Marker()
        }
    }

    fun createChecker(): ChoreographyChecker {
        runVisitor(ChoreographyValidator())
        return ChoreographyChecker(this)
    }

    fun runVisitor(visitor: ASTVisitor): Choreography {
        start.accept(visitor)
        return this
    }
}