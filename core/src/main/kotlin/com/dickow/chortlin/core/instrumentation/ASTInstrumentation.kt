package com.dickow.chortlin.core.instrumentation

import com.dickow.chortlin.core.ast.types.End
import com.dickow.chortlin.core.ast.types.FoundMessage
import com.dickow.chortlin.core.ast.types.Interaction
import com.dickow.chortlin.core.checker.ASTVisitor

class ASTInstrumentation(private val instrumentation: Instrumentation) : ASTVisitor {

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