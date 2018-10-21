package com.dickow.chortlin.core.checker

import com.dickow.chortlin.core.ast.types.End
import com.dickow.chortlin.core.ast.types.FoundMessage
import com.dickow.chortlin.core.ast.types.Interaction

interface ASTVisitor {
    fun visitEnd(astNode: End)
    fun <C> visitFoundMessage(astNode: FoundMessage<C>)
    fun <C1, C2> visitInteraction(astNode: Interaction<C1, C2>)
}