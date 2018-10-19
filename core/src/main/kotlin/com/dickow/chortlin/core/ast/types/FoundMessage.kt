package com.dickow.chortlin.core.ast.types

import com.dickow.chortlin.core.ast.Label
import com.dickow.chortlin.core.choreography.participant.Participant

class FoundMessage<T>(
        private val receiver: Participant<T>,
        private val label: Label,
        override val previous: ASTNode?,
        override var next: ASTNode?) : ASTNode(previous, next) {

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
        result = 31 * result + (previous?.hashCode() ?: 0)
        result = 31 * result + (next?.hashCode() ?: 0)
        return result
    }
}