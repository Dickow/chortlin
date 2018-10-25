package com.dickow.chortlin.core.instrumentation

import com.dickow.chortlin.core.ast.types.*
import com.dickow.chortlin.core.checker.ASTVisitor

class ASTInstrumentation(private val instrumentation: Instrumentation) : ASTVisitor {

    override fun <C> visitFoundMessageReturn(astNode: FoundMessageReturn<C>) {
        instrumentation.after(astNode.receiver)
        astNode.next?.accept(this)
    }

    override fun <C1, C2> visitInteractionReturn(astNode: InteractionReturn<C1, C2>) {
        instrumentation.after(astNode.receiver)
        astNode.next?.accept(this)
    }

    override fun visitEnd(astNode: End) {
        astNode.next?.accept(this)
    }

    override fun <C> visitFoundMessage(astNode: FoundMessage<C>) {
        instrumentation.before(astNode.receiver)
        astNode.next?.accept(this)
    }

    override fun <C1, C2> visitInteraction(astNode: Interaction<C1, C2>) {
        instrumentation.before(astNode.sender)
        instrumentation.before(astNode.receiver)
        astNode.next?.accept(this)
    }
}