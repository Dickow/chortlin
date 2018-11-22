package com.dickow.chortlin.core.ast

import com.dickow.chortlin.core.ast.types.*

interface ASTVisitor {
    fun visitEnd(astNode: End)
    fun <T> visitInteraction(astNode: Interaction<T>)
    fun <T> visitReturnFrom(astNode: ReturnFrom<T>)
    fun visitParallel(astNode: Parallel)
    fun visitChoice(astNode: Choice)
}