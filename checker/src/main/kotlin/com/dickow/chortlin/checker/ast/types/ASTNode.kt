package com.dickow.chortlin.checker.ast.types

import com.dickow.chortlin.checker.ast.ASTVisitor
import com.dickow.chortlin.checker.ast.Label
import com.dickow.chortlin.checker.ast.types.placeholder.Placeholder
import com.dickow.chortlin.checker.checker.SatisfactionRelationship
import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.checker.choreography.ChoreographyBuilder
import com.dickow.chortlin.checker.choreography.method.ChortlinMethod
import com.dickow.chortlin.checker.choreography.participant.Participant
import com.dickow.chortlin.shared.observation.ObservableParticipant

abstract class ASTNode(var previous: ASTNode?, var next: ASTNode?) : ChoreographyBuilder, SatisfactionRelationship {

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

    override fun <C> returnFrom(method: ChortlinMethod<C>, label: String): ChoreographyBuilder {
        val observableReceiver = ObservableParticipant(method.participant.clazz, method.jvmMethod)
        val next = ReturnFrom(observableReceiver, Label(label), this, null)
        this.next = next
        return next
    }

    override fun <C> interaction(sender: Participant, method: ChortlinMethod<C>, label: String): ChoreographyBuilder {
        val observableReceiver = ObservableParticipant(method.participant.clazz, method.jvmMethod)
        val next = Interaction(sender, observableReceiver, Label(label), this, null)
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