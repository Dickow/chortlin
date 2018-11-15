package com.dickow.chortlin.core.ast.types

import com.dickow.chortlin.core.ast.ASTVisitor
import com.dickow.chortlin.core.ast.Label
import com.dickow.chortlin.core.checker.match.*
import com.dickow.chortlin.core.checker.result.CheckResult
import com.dickow.chortlin.core.choreography.participant.Participant
import com.dickow.chortlin.core.trace.Invocation
import com.dickow.chortlin.core.trace.Trace

class Interaction<C1, C2>(
        val sender: Participant<C1>,
        val receiver: Participant<C2>,
        val label: Label,
        previous: ASTNode?,
        next: ASTNode?) : ASTNode(previous, next) {
    private val matcher = Matcher()

    override fun satisfy(trace: Trace): CheckResult {
        val matchResult = matcher.matchTwo(trace.getNotConsumed(), Invocation(sender), Invocation(receiver))
        return when (matchResult) {
            is SuccessfulMatch -> {
                trace.consume(matchResult.matchedElements)
                next!!.satisfy(trace)
            }
            is PartialMatch -> {
                trace.consume(matchResult.matchedElements)
                CheckResult.Partial
            }
            is InvalidTraceMatch -> CheckResult.None
            is NoMoreTraceMatch -> CheckResult.Partial
            else -> CheckResult.None
        }
    }

    override fun accept(visitor: ASTVisitor) {
        visitor.visitInteraction(this)
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Interaction<*, *>) {
            sender == other.sender && receiver == other.receiver && label == other.label && super.equals(other)
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + sender.hashCode()
        result = 31 * result + receiver.hashCode()
        result = 31 * result + label.hashCode()
        return result
    }
}