package com.dickow.chortlin.core.instrumentation

import com.dickow.chortlin.core.ast.ASTVisitor
import com.dickow.chortlin.core.ast.types.*

class ASTInstrumentation(private val instrumentation: Instrumentation) : ASTVisitor {
    override fun visitChoice(astNode: Choice) {
        astNode.possiblePaths.forEach { choreography -> choreography.runVisitor(this) }
    }

    override fun visitParallel(astNode: Parallel) {
        astNode.parallelChoreography.runVisitor(this)
        astNode.next?.accept(this)
    }

    override fun <C> visitReturnFrom(astNode: ReturnFrom<C>) {
        instrumentation.after(astNode.participant)
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