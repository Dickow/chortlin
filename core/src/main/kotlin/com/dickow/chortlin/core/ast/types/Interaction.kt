package com.dickow.chortlin.core.ast.types

import com.dickow.chortlin.core.ast.Label
import com.dickow.chortlin.core.choreography.participant.Participant

class Interaction<C1, C2>(
        private val sender: Participant<C1>,
        private val receiver: Participant<C2>,
        private val label: Label,
        override val previous: ASTNode?,
        override var next: ASTNode?) : ASTNode(previous, next) {

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
        result = 31 * result + (previous?.hashCode() ?: 0)
        result = 31 * result + (next?.hashCode() ?: 0)
        return result
    }
}