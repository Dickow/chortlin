package com.dickow.chortlin.checker.ast.validation

import com.dickow.chortlin.checker.ast.ASTVisitor
import com.dickow.chortlin.checker.ast.types.*
import com.dickow.chortlin.shared.exceptions.InvalidASTException
import java.util.function.Predicate

class ASTValidator : ASTVisitor {
    private val scope: ValidationScope<ASTNode> = ValidationScope()

    override fun visitChoice(astNode: Choice) {
        astNode.possiblePaths.forEach { node -> node.accept(this) }
    }

    override fun visitEnd(astNode: End) {
        if (astNode.next != null) {
            throw InvalidASTException("Found an end node with subsequent activities, this is not allowed. " +
                    "The error occurred after the node: ${System.lineSeparator()} ${astNode.previous}")
        }
    }

    override fun visitReturnFrom(astNode: ReturnFrom) {
        if (!hasMatchingInvocation(astNode)) {
            throw InvalidASTException("Found a return node with no matching invocation node. " +
                    "The node causing the error was $astNode")
        }
        else {
            scope.beginNewScope(astNode)
            nextNode(astNode)
            scope.exitScope()
        }
    }

    override fun visitInteraction(astNode: Interaction) {
        scope.beginNewScope(astNode)
        nextNode(astNode)
        scope.exitScope()
    }

    private fun nextNode(astNode: ASTNode) {
        if (astNode.next == null && (astNode !is End || astNode !is Choice)) {
            throw InvalidASTException("Encountered a path without a proper definition at: $astNode ${System.lineSeparator()}" +
                    "Have you added a correct end to your choreography?")
        } else {
            astNode.next?.accept(this)
        }
    }

    private fun hasMatchingInvocation(astNode: ReturnFrom): Boolean {
        return scope.hasOpen(Predicate { node ->
            when (node) {
                is Interaction -> node.receiver == astNode.participant
                else -> false
            } })
    }
}