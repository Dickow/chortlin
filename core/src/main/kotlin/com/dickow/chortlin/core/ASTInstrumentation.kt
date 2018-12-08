package com.dickow.chortlin.core

import com.dickow.chortlin.checker.ast.ASTVisitor
import com.dickow.chortlin.checker.ast.types.*
import com.dickow.chortlin.interception.instrumentation.Instrumentation


class ASTInstrumentation(private val instrumentation: Instrumentation) : ASTVisitor {
    override fun visitChoice(astNode: Choice) {
        astNode.possiblePaths.forEach { choreography -> choreography.runVisitor(this) }
    }

    override fun visitParallel(astNode: Parallel) {
        astNode.parallelChoreography.runVisitor(this)
        astNode.next?.accept(this)
    }

    override fun visitReturnFrom(astNode: ReturnFrom) {
        instrumentation.after(astNode.participant)
        astNode.next?.accept(this)
    }

    override fun visitEnd(astNode: End) {
        astNode.next?.accept(this)
    }

    override fun visitInteraction(astNode: Interaction) {
        instrumentation.before(astNode.receiver)
        astNode.next?.accept(this)
    }
}