package com.dickow.chortlin.core.ast.types

import com.dickow.chortlin.core.ast.ASTVisitor

class   End(override val previous: ASTNode?, override var next: ASTNode?) : ASTNode(previous, next) {
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