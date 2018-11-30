package com.dickow.chortlin.core.ast

import com.dickow.chortlin.core.ast.types.*

interface ASTVisitor {
    fun visitEnd(astNode: End)
    fun visitInteraction(astNode: Interaction)
    fun visitReturnFrom(astNode: ReturnFrom)
    fun visitParallel(astNode: Parallel)
    fun visitChoice(astNode: Choice)
}