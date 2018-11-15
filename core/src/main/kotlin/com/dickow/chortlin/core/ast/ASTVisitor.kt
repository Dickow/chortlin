package com.dickow.chortlin.core.ast

import com.dickow.chortlin.core.ast.types.*

interface ASTVisitor {
    fun visitEnd(astNode: End)
    fun <C> visitFoundMessage(astNode: FoundMessage<C>)
    fun <C1, C2> visitInteraction(astNode: Interaction<C1, C2>)
    fun <C> visitReturnFrom(astNode: ReturnFrom<C>)
    fun visitParallel(astNode: Parallel)
    fun visitChoice(astNode: Choice)
}