package com.dickow.chortlin.core.checker

import com.dickow.chortlin.core.ast.ASTVisitor
import com.dickow.chortlin.core.ast.types.*
import com.dickow.chortlin.core.checker.pattern.*
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.exceptions.InvalidASTException
import com.dickow.chortlin.core.shared.Scope
import com.dickow.chortlin.core.trace.Invocation
import com.dickow.chortlin.core.trace.Return
import com.dickow.chortlin.core.trace.Trace

class ChoreographyChecker : ASTVisitor {
    constructor(choreography: Choreography) {
        this.choreography = choreography
        this.scope = Scope()
        pattern = init()
    }

    constructor(choreography: Choreography, inheritedScope: Scope<Pattern>) {
        this.choreography = choreography
        this.scope = inheritedScope
        pattern = init()
    }

    val pattern : Pattern
    private val choreography: Choreography
    private val scope: Scope<Pattern>
    private var latestScope: Pattern? = null

    override fun visitChoice(astNode: Choice) {
        val previous = scope.getCurrentScope()
        val patterns = astNode.possiblePaths.map { ChoreographyChecker(it, scope).pattern }
        val choicePattern = OneOfPattern(patterns, previous, null)
        handleCurrentNode(astNode, choicePattern)
    }

    override fun visitParallel(astNode: Parallel) {
        val previous = scope.getCurrentScope()
        val parallelChoreography = ChoreographyChecker(astNode.parallelChoreography, scope).pattern
        val parallelPattern = MultiPattern(parallelChoreography, previous, null)
        parallelChoreography.previous = parallelPattern // Assign the multi pattern as the previous for both
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
        val wasMatched = pattern.match(trace)
        return wasMatched && trace.getNotConsumed().isEmpty()
    }

    private fun handleCurrentNode(astNode: ASTNode, pattern: Pattern) {
        if (scope.isInScope()) {
            scope.getCurrentScope()!!.child = pattern
        }
        scope.beginNewScope(pattern)
        astNode.next?.accept(this)
        this.latestScope = scope.getCurrentScope()
        scope.exitScope()
    }

    private fun init(): Pattern {
        choreography.start.accept(this)
        return when {
            latestScope != null -> latestScope!!
            scope.hasOuterScope() -> scope.getOuterScope()!!
            else -> throw InvalidASTException(
                    "Unable to build a correct pattern matching tree, did you make an error in your choreography?")
        }
    }
}