package com.dickow.chortlin.checker.ast.types

import com.dickow.chortlin.checker.ast.ASTVisitor
import com.dickow.chortlin.checker.checker.result.CheckResult
import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.shared.trace.Trace

class Parallel(
        val parallelChoreography: Choreography,
        previous: ASTNode?,
        next: ASTNode?) : ASTNode(previous, next) {

    override fun satisfy(trace: Trace): CheckResult {
        return CheckResult.None
    }

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