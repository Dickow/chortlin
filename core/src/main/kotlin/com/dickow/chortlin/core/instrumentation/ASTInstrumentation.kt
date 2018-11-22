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

    override fun <T> visitInteraction(astNode: Interaction<T>) {
        instrumentation.before(astNode.receiver)
        astNode.next?.accept(this)
    }
}