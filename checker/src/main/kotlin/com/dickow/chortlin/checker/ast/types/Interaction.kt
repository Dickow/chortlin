package com.dickow.chortlin.checker.ast.types

import com.dickow.chortlin.checker.ast.ASTVisitor
import com.dickow.chortlin.checker.ast.Label
import com.dickow.chortlin.checker.checker.match.InvalidTraceMatch
import com.dickow.chortlin.checker.checker.match.Matcher
import com.dickow.chortlin.checker.checker.match.NoMoreTraceMatch
import com.dickow.chortlin.checker.checker.match.SuccessfulMatch
import com.dickow.chortlin.checker.checker.result.CheckResult
import com.dickow.chortlin.checker.choreography.participant.Participant
import com.dickow.chortlin.shared.observation.ObservableParticipant
import com.dickow.chortlin.shared.trace.Trace


class Interaction(
        val sender: Participant,
        val receiver: ObservableParticipant,
        val label: Label,
        previous: ASTNode?,
        next: ASTNode?) : ASTNode(previous, next) {
    private val matcher = Matcher()

    override fun satisfy(trace: Trace): CheckResult {
        val matchResult = matcher.matchInvocation(trace.getNotConsumed(), receiver)
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
        visitor.visitInteraction(this)
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Interaction) {
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