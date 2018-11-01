package com.dickow.chortlin.core.ast.types

import com.dickow.chortlin.core.ast.ASTVisitor
import com.dickow.chortlin.core.ast.Label
import com.dickow.chortlin.core.ast.types.placeholder.Placeholder
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.ChoreographyBuilder
import com.dickow.chortlin.core.choreography.participant.Participant

abstract class ASTNode(var previous: ASTNode?, var next: ASTNode?) : ChoreographyBuilder {

    abstract fun accept(visitor: ASTVisitor)

    override fun choice(vararg possiblePaths: (ChoreographyBuilder) -> Choreography): Choreography {
        val paths = possiblePaths.map { p -> p(Choreography.builder()) }
        val next = Choice(paths, this)
        paths.forEach { p -> p.start.previous = next }
        this.next = next
        return build()
    }

    override fun parallel(path: (ChoreographyBuilder) -> Choreography): ChoreographyBuilder {
        val parallelPath = path(Choreography.builder())
        val next = Parallel(parallelPath, this, null)
        parallelPath.start.previous = next
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
        return if (previous is Placeholder) {
            this
        } else {
            previous?.root() ?: this
        }
    }
}