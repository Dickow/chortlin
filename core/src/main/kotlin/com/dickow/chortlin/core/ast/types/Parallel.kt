package com.dickow.chortlin.core.ast.types

import com.dickow.chortlin.core.ast.ASTVisitor
import com.dickow.chortlin.core.choreography.Choreography

class Parallel(
        val parallelChoreography: Choreography,
        previous: ASTNode?,
        next: ASTNode?) : ASTNode(previous, next) {

    override fun accept(visitor: ASTVisitor) {
        visitor.visitParallel(this)
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Parallel) {
            this.parallelChoreography == other.parallelChoreography && super.equals(other)
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + parallelChoreography.hashCode()
        return result
    }
}