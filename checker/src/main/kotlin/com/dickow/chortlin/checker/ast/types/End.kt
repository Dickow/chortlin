package com.dickow.chortlin.checker.ast.types

import com.dickow.chortlin.checker.ast.ASTVisitor
import com.dickow.chortlin.checker.checker.result.CheckResult
import com.dickow.chortlin.shared.trace.Trace

class End(previous: ASTNode?) : ASTNode(previous, null) {

    override fun satisfy(trace: Trace): CheckResult {
        val satisfied = trace.getNotConsumed().size == 0
        return if (satisfied) CheckResult.Full else CheckResult.None
    }

    override fun accept(visitor: ASTVisitor) {
        visitor.visitEnd(this)
    }

    override fun equals(other: Any?): Boolean {
        return other is End && super.equals(other)
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (previous?.hashCode() ?: 0)
        result = 31 * result + (next?.hashCode() ?: 0)
        return result
    }
}