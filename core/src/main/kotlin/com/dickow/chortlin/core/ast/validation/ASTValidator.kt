package com.dickow.chortlin.core.ast.validation

import com.dickow.chortlin.core.ast.ASTVisitor
import com.dickow.chortlin.core.ast.types.*
import com.dickow.chortlin.core.exceptions.InvalidASTException
import java.util.function.Predicate

class ASTValidator : ASTVisitor {
    private val scope: ValidationScope<ASTNode> = ValidationScope()

    override fun visitParallel(astNode: Parallel) {
        astNode.accept(this)
        nextNode(astNode)
    }

    override fun visitEnd(astNode: End) {
        if (astNode.next != null) {
            throw InvalidASTException("Found an end node with subsequent activities, this is not allowed. " +
                    "The error occurred after the node: ${System.lineSeparator()} ${astNode.previous}")
        }
    }

    override fun <C> visitFoundMessage(astNode: FoundMessage<C>) {
        scope.beginNewScope(astNode)
        nextNode(astNode)
        scope.exitScope()
    }

    override fun <C> visitFoundMessageReturn(astNode: FoundMessageReturn<C>) {
        if (!hasMatchingInvocation(astNode)) {
            throw InvalidASTException("Found a foundMessage return node with no matching invocation node." +
                    "The node causing the error was $astNode")
        } else {
            scope.beginNewScope(astNode)
            nextNode(astNode)
            scope.exitScope()
        }
    }

    override fun <C1, C2> visitInteraction(astNode: Interaction<C1, C2>) {
        scope.beginNewScope(astNode)
        nextNode(astNode)
        scope.exitScope()
    }

    override fun <C1, C2> visitInteractionReturn(astNode: InteractionReturn<C1, C2>) {
        if (!hasMatchingInvocation(astNode)) {
            throw InvalidASTException("Found an interaction return node with no matching invocation node." +
                    "The node causing the error was $astNode")
        } else {
            scope.beginNewScope(astNode)
            nextNode(astNode)
            scope.exitScope()
        }
    }

    private fun nextNode(astNode: ASTNode) {
        if (astNode.next == null && astNode !is End) {
            throw InvalidASTException("Encountered a path without an END at: $astNode")
        } else {
            astNode.next?.accept(this)
        }
    }

    private fun <C1, C2> hasMatchingInvocation(astNode: InteractionReturn<C1, C2>): Boolean {
        return scope.hasOpen(Predicate { node ->
            node is Interaction<*, *>
                    && node.receiver == astNode.receiver
                    && node.sender == astNode.sender
        })
    }

    private fun <C> hasMatchingInvocation(astNode: FoundMessageReturn<C>) =
            scope.hasOpen(Predicate { node -> node is FoundMessage<*> && node.receiver == astNode.receiver })
}