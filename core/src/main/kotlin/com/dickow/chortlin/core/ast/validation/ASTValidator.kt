package com.dickow.chortlin.core.ast.validation

import com.dickow.chortlin.core.ast.ASTVisitor
import com.dickow.chortlin.core.ast.types.*
import com.dickow.chortlin.core.exceptions.InvalidASTException
import java.util.function.Predicate

class ASTValidator : ASTVisitor {
    private val scope: ValidationScope<ASTNode> = ValidationScope()

    override fun visitChoice(astNode: Choice) {
        astNode.possiblePaths.forEach { node -> node.runVisitor(this) }
    }

    override fun visitParallel(astNode: Parallel) {
        astNode.parallelChoreography.runVisitor(this)
        nextNode(astNode)
    }

    override fun visitEnd(astNode: End) {
        if (astNode.next != null) {
            throw InvalidASTException("Found an end node with subsequent activities, this is not allowed. " +
                    "The error occurred after the node: ${System.lineSeparator()} ${astNode.previous}")
        }
    }

    override fun <C> visitReturnFrom(astNode: ReturnFrom<C>) {
        if (!hasMatchingInvocation(astNode)) {
            throw InvalidASTException("Found a foundMessage return node with no matching invocation node. " +
                    "The node causing the error was $astNode")
        } else {
            scope.beginNewScope(astNode)
            nextNode(astNode)
            scope.exitScope()
        }
    }

    override fun <T> visitInteraction(astNode: Interaction<T>) {
        scope.beginNewScope(astNode)
        nextNode(astNode)
        scope.exitScope()
    }

    private fun nextNode(astNode: ASTNode) {
        if (astNode.next == null && astNode !is End) {
            throw InvalidASTException("Encountered a path without an END at: $astNode")
        } else {
            astNode.next?.accept(this)
        }
    }

    private fun <C> hasMatchingInvocation(astNode: ReturnFrom<C>) : Boolean{
        return scope.hasOpen(Predicate { node ->
            when (node) {
                is Interaction<*> -> node.receiver == astNode.participant
                else -> false
            } })
    }
}