package com.dickow.chortlin.core.ast.types

import com.dickow.chortlin.core.ast.ASTVisitor
import com.dickow.chortlin.core.ast.Label
import com.dickow.chortlin.core.checker.match.*
import com.dickow.chortlin.core.checker.result.CheckResult
import com.dickow.chortlin.core.choreography.participant.Participant
import com.dickow.chortlin.core.trace.Return
import com.dickow.chortlin.core.trace.Trace

class ReturnFrom<C>(
        val participant: Participant<C>,
        val label: Label,
        previous: ASTNode?,
        next: ASTNode?) : ASTNode(previous, next) {
    private val matcher = Matcher()

    override fun satisfy(trace: Trace): CheckResult {
        val matchResult = matcher.matchOne(trace.getNotConsumed(), Return(participant))
        return when (matchResult) {
            is SuccessfulMatch -> {
                trace.consume(matchResult.matchedElements)
                next!!.satisfy(trace)
            }
            is PartialMatch -> CheckResult.None
            is InvalidTraceMatch -> CheckResult.None
            is NoMoreTraceMatch -> CheckResult.Partial
            else -> CheckResult.None
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