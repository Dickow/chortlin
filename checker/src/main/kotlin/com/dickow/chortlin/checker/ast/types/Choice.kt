package com.dickow.chortlin.checker.ast.types

import com.dickow.chortlin.checker.ast.ASTVisitor
import com.dickow.chortlin.checker.checker.result.CheckResult
import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.checker.trace.Trace


class Choice(val possiblePaths: List<Choreography>, previous: ASTNode?) : ASTNode(previous, null) {

    override fun satisfy(trace: Trace): CheckResult {
        return CheckResult.None
    }

    override fun accept(visitor: ASTVisitor) {
        visitor.visitChoice(this)
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Choice) {
            this.possiblePaths == other.possiblePaths && super.equals(other)
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + possiblePaths.hashCode()
        return result
    }

    override fun toString(): String {
        return "Choice(${possiblePaths.joinToString(", ") { c -> c.start.toString() }})"
    }
}