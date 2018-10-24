package com.dickow.chortlin.core.choreography

import com.dickow.chortlin.core.ast.types.ASTNode
import com.dickow.chortlin.core.ast.types.Marker
import com.dickow.chortlin.core.checker.ASTVisitor
import com.dickow.chortlin.core.checker.ChoreographyChecker
import com.dickow.chortlin.core.instrumentation.ASTInstrumentation

data class Choreography(val start: ASTNode) {
    companion object Instance {
        fun builder(): ChoreographyBuilder {
            return Marker()
        }
    }

    fun createChecker(): ChoreographyChecker {
        return ChoreographyChecker(this)
    }

    fun applyInstrumentation(instrumentationVisitor: ASTInstrumentation): Choreography {
        runVisitor(instrumentationVisitor)
        return this
    }

    fun runVisitor(visitor: ASTVisitor) {
        start.accept(visitor)
    }
}