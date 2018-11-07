package com.dickow.chortlin.core.ast.types

import com.dickow.chortlin.core.ast.ASTVisitor
import com.dickow.chortlin.core.ast.Label
import com.dickow.chortlin.core.checker.match.Matcher
import com.dickow.chortlin.core.choreography.participant.Participant
import com.dickow.chortlin.core.trace.Return
import com.dickow.chortlin.core.trace.Trace

class ReturnFrom<C>(
        val participant: Participant<C>,
        val label: Label,
        previous: ASTNode?,
        next: ASTNode?) : ASTNode(previous, next) {
    private val matcher = Matcher()

    override fun satisfy(trace: Trace): Boolean {
        val matchResult = matcher.matchOne(trace.getNotConsumed(), Return(participant))
        return when(matchResult.matched){
            true -> {
                trace.consume(matchResult.matchedElements[0])
                next!!.satisfy(trace)
            }
            else -> false
        }
    }

    override fun accept(visitor: ASTVisitor) {
        visitor.visitReturnFrom(this)
    }

    override fun equals(other: Any?): Boolean {
        return if (other is ReturnFrom<*>) {
            this.participant == other.participant && this.label == other.label && super.equals(other)
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + participant.hashCode()
        result = 31 * result + label.hashCode()
        return result
    }

}