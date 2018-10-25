package com.dickow.chortlin.core.ast.types

import com.dickow.chortlin.core.ast.ASTVisitor
import com.dickow.chortlin.core.choreography.Choreography

class Parallel(
        private val parallelChoreography: Choreography,
        previous: ASTNode?,
        next: ASTNode?) : ASTNode(previous, next) {

    override fun accept(visitor: ASTVisitor) {
        parallelChoreography.runVisitor(visitor)
    }
}