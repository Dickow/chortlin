package com.dickow.chortlin.core.checker

import com.dickow.chortlin.core.ast.ASTVisitor
import com.dickow.chortlin.core.ast.types.*
import com.dickow.chortlin.core.checker.pattern.DoublePattern
import com.dickow.chortlin.core.checker.pattern.MultiPattern
import com.dickow.chortlin.core.checker.pattern.Pattern
import com.dickow.chortlin.core.checker.pattern.SinglePattern
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.exceptions.InvalidASTException
import com.dickow.chortlin.core.shared.Scope
import com.dickow.chortlin.core.trace.Invocation
import com.dickow.chortlin.core.trace.Return
import com.dickow.chortlin.core.trace.Trace

class ChoreographyChecker(choreography: Choreography) : ASTVisitor {
    val pattern : Pattern
    private val scope = Scope<Pattern>()

    init {
        choreography.start.accept(this)
        if (scope.hasOuterScope()) {
            pattern = scope.getOuterScope()!!
        } else {
            throw InvalidASTException(
                    "Unable to build a correct pattern matching tree, did you make an error in your choreography?")
        }
    }

    override fun visitParallel(astNode: Parallel) {
        val previous = scope.getCurrentScope()
        val parallelChoreography = Choreography(astNode).createChecker().pattern
        val parallelPattern = MultiPattern(parallelChoreography, previous, null)
        handleCurrentNode(astNode, parallelPattern)
    }

    override fun <C> visitFoundMessageReturn(astNode: FoundMessageReturn<C>) {
        val previous = scope.getCurrentScope()
        val foundMessageReturnPattern = SinglePattern(Return(astNode.receiver), previous, null)
        handleCurrentNode(astNode, foundMessageReturnPattern)
    }

    override fun <C1, C2> visitInteractionReturn(astNode: InteractionReturn<C1, C2>) {
        val previous = scope.getCurrentScope()
        val interactionReturnPattern = SinglePattern(Return(astNode.receiver), previous, null)
        handleCurrentNode(astNode, interactionReturnPattern)
    }

    override fun visitEnd(astNode: End) {
        astNode.next?.accept(this)
    }

    override fun <C> visitFoundMessage(astNode: FoundMessage<C>) {
        val previous = scope.getCurrentScope()
        val foundPattern = SinglePattern(Invocation(astNode.receiver), previous, null)
        handleCurrentNode(astNode, foundPattern)
    }

    override fun <C1, C2> visitInteraction(astNode: Interaction<C1, C2>) {
        val previous = scope.getCurrentScope()
        val pattern = DoublePattern(Invocation(astNode.sender), Invocation(astNode.receiver), previous, null)
        handleCurrentNode(astNode, pattern)
    }

    fun check(trace : Trace) : Boolean{
        trace.markAllNonConsumed()
        return pattern.match(trace)
    }

    private fun handleCurrentNode(astNode: ASTNode, pattern: Pattern) {
        if (scope.isInScope()) {
            scope.getCurrentScope()!!.child = pattern
        }
        scope.beginNewScope(pattern)
        astNode.next?.accept(this)
        scope.exitScope()
    }
}