package com.dickow.chortlin.core.choreography

import com.dickow.chortlin.core.ast.ASTVisitor
import com.dickow.chortlin.core.ast.types.ASTNode
import com.dickow.chortlin.core.ast.types.placeholder.Marker
import com.dickow.chortlin.core.ast.validation.ASTValidator
import com.dickow.chortlin.core.checker.ChoreographyChecker

data class Choreography(val start: ASTNode) {
    companion object Instance {
        fun builder(): ChoreographyBuilder {
            return Marker()
        }
    }

    fun createChecker(): ChoreographyChecker {
        runVisitor(ASTValidator())
        return ChoreographyChecker(this)
    }

    fun runVisitor(visitor: ASTVisitor): Choreography {
        start.accept(visitor)
        return this
    }
}