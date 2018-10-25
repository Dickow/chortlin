package com.dickow.chortlin.core.ast.types

import com.dickow.chortlin.core.ast.ASTVisitor
import com.dickow.chortlin.core.ast.Label
import com.dickow.chortlin.core.choreography.participant.Participant

class FoundMessage<T>(
        val receiver: Participant<T>,
        val label: Label,
        previous: ASTNode?,
        next: ASTNode?) : ASTNode(previous, next) {

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