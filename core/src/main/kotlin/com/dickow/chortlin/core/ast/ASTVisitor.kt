package com.dickow.chortlin.core.ast

import com.dickow.chortlin.core.ast.types.*

interface ASTVisitor {
    fun visitEnd(astNode: End)
    fun <C> visitFoundMessage(astNode: FoundMessage<C>)
    fun <C> visitFoundMessageReturn(astNode: FoundMessageReturn<C>)
    fun <C1, C2> visitInteraction(astNode: Interaction<C1, C2>)
    fun <C1, C2> visitInteractionReturn(astNode: InteractionReturn<C1, C2>)
    fun visitParallel(astNode: Parallel)
}