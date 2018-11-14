package com.dickow.chortlin.core.ast.types

import com.dickow.chortlin.core.ast.ASTVisitor
import com.dickow.chortlin.core.ast.Label
import com.dickow.chortlin.core.checker.CheckResult
import com.dickow.chortlin.core.checker.match.*
import com.dickow.chortlin.core.choreography.participant.Participant
import com.dickow.chortlin.core.trace.Invocation
import com.dickow.chortlin.core.trace.Trace

class FoundMessage<T>(
        val receiver: Participant<T>,
        val label: Label,
        previous: ASTNode?,
        next: ASTNode?) : ASTNode(previous, next) {
    private val matcher = Matcher()

    override fun satisfy(trace: Trace): CheckResult {
        val matchResult = matcher.matchOne(trace.getNotConsumed(), Invocation(receiver))
        return when(matchResult){
            is SuccessfulMatch -> {
                trace.consume(matchResult.matchedElements[0])
                next!!.satisfy(trace)
            }
            is PartialMatch -> {}
            is NoMoreTraceMatch -> {}
            is InvalidTraceMatch -> CheckResult(false, false)
        }
//        return when(matchResult.matched){
//            true -> {
//
//            }
//            else -> false
//        }
    }

    override fun accept(visitor: ASTVisitor) {
        visitor.visitFoundMessage(this)
    }

    override fun equals(other: Any?): Boolean {
        return if (other is FoundMessage<*>) {
            this.receiver == other.receiver && this.label == other.label && super.equals(other)
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + receiver.hashCode()
        result = 31 * result + label.hashCode()
        return result
    }
}