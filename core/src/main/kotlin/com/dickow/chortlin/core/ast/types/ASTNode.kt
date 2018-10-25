package com.dickow.chortlin.core.ast.types

import com.dickow.chortlin.core.ast.ASTVisitor
import com.dickow.chortlin.core.ast.Label
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.ChoreographyBuilder
import com.dickow.chortlin.core.choreography.participant.Participant

abstract class ASTNode(val previous: ASTNode?, var next: ASTNode?) : ChoreographyBuilder {

    abstract fun accept(visitor: ASTVisitor)

    override fun parallel(choreography: (ChoreographyBuilder) -> Choreography): ChoreographyBuilder {
        val next = Parallel(choreography(Choreography.builder()), this, null)
        this.next = next
        return next
    }

    override fun <C> foundMessageReturn(receiver: Participant<C>, label: String): ChoreographyBuilder {
        val next = FoundMessageReturn(receiver, Label(label), this, null)
        this.next = next
        return next
    }

    override fun <C1, C2> interactionReturn(sender: Participant<C1>, receiver: Participant<C2>, label: String): ChoreographyBuilder {
        val next = InteractionReturn(sender, receiver, Label(label), this, null)
        this.next = next
        return next
    }

    override fun <C> foundMessage(receiver: Participant<C>, label: String): ChoreographyBuilder {
        val next = FoundMessage(receiver, Label(label), this, null)
        this.next = next
        return next
    }

    override fun <C1, C2> interaction(sender: Participant<C1>, receiver: Participant<C2>, label: String): ChoreographyBuilder {
        val next = Interaction(sender, receiver, Label(label), this, null)
        this.next = next
        return next
    }

    override fun end(): ChoreographyBuilder {
        val next = End(this, null)
        this.next = next
        return next
    }

    override fun build(): Choreography {
        return Choreography(root())
    }

    override fun toString(): String {
        val previousNode = previous?.javaClass?.simpleName ?: "NULL"
        val nextNode = next?.javaClass?.simpleName ?: "NULL"
        return "${this.javaClass.simpleName}($previousNode, $nextNode)"
    }

    override fun equals(other: Any?): Boolean {
        return if (other is ASTNode) {
            if (this.next == null) other.next == null else this.next?.equals(other.next) ?: false
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = previous?.hashCode() ?: 0
        result = 31 * result + (next?.hashCode() ?: 0)
        return result
    }

    private fun root(): ASTNode {
        return previous?.root() ?: this
    }
}