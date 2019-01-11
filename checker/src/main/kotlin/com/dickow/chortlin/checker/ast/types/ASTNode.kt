package com.dickow.chortlin.checker.ast.types

import com.dickow.chortlin.checker.ast.ASTBuilder
import com.dickow.chortlin.checker.ast.ASTVisitor
import com.dickow.chortlin.checker.ast.Label
import com.dickow.chortlin.checker.checker.SatisfactionRelationship
import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.checker.choreography.participant.ObservableParticipant
import com.dickow.chortlin.checker.choreography.participant.Participant

abstract class ASTNode(var previous: ASTNode?, var next: ASTNode?) : ASTBuilder, SatisfactionRelationship {

    abstract fun accept(visitor: ASTVisitor)

    override fun choice(branches: List<Choreography>): Choreography {
        val next = Choice(branches.map { c -> c.start }, this)
        this.next = next
        return build()
    }

    override fun returnFrom(receiver: ObservableParticipant, label: String): ASTBuilder {
        val next = ReturnFrom(receiver, Label(label), this, null)
        this.next = next
        return next
    }

    override fun interaction(sender: Participant, receiver: ObservableParticipant, label: String): ASTBuilder {
        val next = Interaction(sender, receiver, Label(label), this, null)
        this.next = next
        return next
    }

    override fun end(): Choreography {
        val next = End(this)
        this.next = next
        return build()
    }

    override fun toString(): String {
        val previousNode = previous?.javaClass?.simpleName ?: "NULL"
        val nextNode = next?.javaClass?.simpleName ?: "NULL"
        return "${this.javaClass.simpleName}($previousNode, $nextNode)"
    }

    override fun equals(other: Any?): Boolean {
        return if (other is ASTNode) {
            if (this.next == null) {
                other.next == null
            } else {
                this.next?.equals(other.next) ?: false
            }
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = previous?.hashCode() ?: 0
        result = 31 * result + (next?.hashCode() ?: 0)
        return result
    }

    private fun build(): Choreography {
        return Choreography(root())
    }

    private fun root(): ASTNode {
        return previous?.root() ?: this
    }
}