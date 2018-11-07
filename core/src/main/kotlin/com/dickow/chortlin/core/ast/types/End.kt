package com.dickow.chortlin.core.ast.types

import com.dickow.chortlin.core.ast.ASTVisitor
import com.dickow.chortlin.core.trace.Trace

class End(previous: ASTNode?) : ASTNode(previous, null) {

    override fun satisfy(trace: Trace): Boolean {
        return trace.getNotConsumed().size == 0
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