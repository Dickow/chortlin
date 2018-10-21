package com.dickow.chortlin.core.checker

import com.dickow.chortlin.core.ast.types.ASTNode
import com.dickow.chortlin.core.ast.types.End
import com.dickow.chortlin.core.ast.types.FoundMessage
import com.dickow.chortlin.core.ast.types.Interaction
import com.dickow.chortlin.core.checker.pattern.DoublePattern
import com.dickow.chortlin.core.checker.pattern.EmptyPattern
import com.dickow.chortlin.core.checker.pattern.Pattern
import com.dickow.chortlin.core.checker.pattern.SinglePattern
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.trace.Trace
import com.dickow.chortlin.core.trace.TraceElement
import java.util.*

class ChoreographyChecker(private val choreography: Choreography) : ASTVisitor {
    val pattern : Pattern
    private val workingNode : Stack<Pattern> = Stack()

    init {
        choreography.start.accept(this)
        pattern = workingNode.pop()
    }

    override fun visitEnd(astNode: End) {
        val endPattern = EmptyPattern(LinkedList())
        handleCurrentNode(endPattern)
        handleNextNode(astNode, endPattern)
    }

    override fun <C> visitFoundMessage(astNode: FoundMessage<C>) {
        val foundPattern = SinglePattern(TraceElement(astNode.receiver), LinkedList())
        handleCurrentNode(foundPattern)
        handleNextNode(astNode, foundPattern)
    }

    override fun <C1, C2> visitInteraction(astNode: Interaction<C1, C2>) {
        val pattern = DoublePattern(TraceElement(astNode.sender), TraceElement(astNode.receiver), LinkedList())
        handleCurrentNode(pattern)
        handleNextNode(astNode, pattern)
    }

    fun check(trace : Trace) : Boolean{
        return pattern.match(trace)
    }

    private fun handleCurrentNode(pattern: Pattern) {
        if (workingNode.isEmpty()) {
            workingNode.push(pattern)
        } else {
            workingNode.peek().addChild(pattern)
        }
    }

    private fun handleNextNode(astNode: ASTNode, pattern: Pattern) {
        val next = astNode.next
        if (next != null && workingNode.peek() !== pattern) {
            workingNode.push(pattern)
            next.accept(this)
        }
    }
}