package com.dickow.chortlin.core.ast.types

import com.dickow.chortlin.core.ast.ASTVisitor
import com.dickow.chortlin.core.ast.Label
import com.dickow.chortlin.core.checker.match.InvalidTraceMatch
import com.dickow.chortlin.core.checker.match.Matcher
import com.dickow.chortlin.core.checker.match.NoMoreTraceMatch
import com.dickow.chortlin.core.checker.match.SuccessfulMatch
import com.dickow.chortlin.core.checker.result.CheckResult
import com.dickow.chortlin.core.choreography.participant.Participant
import com.dickow.chortlin.core.trace.Trace

class ReturnFrom(
        val participant: Participant,
        val label: Label,
        previous: ASTNode?,
        next: ASTNode?) : ASTNode(previous, next) {
    private val matcher = Matcher()

    override fun satisfy(trace: Trace): CheckResult {
        val matchResult = matcher.matchReturn(trace.getNotConsumed(), participant)
        return when (matchResult) {
            is SuccessfulMatch -> {
                trace.consume(matchResult.matchedElement)
                next!!.satisfy(trace)
            }
            is InvalidTraceMatch -> CheckResult.None
            is NoMoreTraceMatch -> CheckResult.Partial
            else -> CheckResult.None
        }
    }

    override fun accept(visitor: ASTVisitor) {
        visitor.visitReturnFrom(this)
    }

    override fun equals(other: Any?): Boolean {
        return if (other is ReturnFrom) {
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