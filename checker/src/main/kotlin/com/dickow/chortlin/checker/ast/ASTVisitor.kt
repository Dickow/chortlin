package com.dickow.chortlin.checker.ast

import com.dickow.chortlin.checker.ast.types.*

interface ASTVisitor {
    fun visitEnd(astNode: End)
    fun visitInteraction(astNode: Interaction)
    fun visitReturnFrom(astNode: ReturnFrom)
    fun visitChoice(astNode: Choice)
}