package com.dickow.chortlin.core.checker

import com.dickow.chortlin.core.ast.ASTVisitor
import com.dickow.chortlin.core.ast.types.*
import com.dickow.chortlin.core.choreography.participant.Participant
import java.util.*

class ParticipantRetriever : ASTVisitor {
    private val participants = HashSet<Participant<*>>()

    override fun visitEnd(astNode: End) {
    }

    override fun <C> visitFoundMessage(astNode: FoundMessage<C>) {
        participants.add(astNode.receiver)
        astNode.next?.accept(this)
    }

    override fun <C1, C2> visitInteraction(astNode: Interaction<C1, C2>) {
        participants.add(astNode.sender)
        participants.add(astNode.receiver)
        astNode.next?.accept(this)
    }

    override fun <C> visitReturnFrom(astNode: ReturnFrom<C>) {
        participants.add(astNode.participant)
        astNode.next?.accept(this)
    }

    override fun visitParallel(astNode: Parallel) {
    }

    override fun visitChoice(astNode: Choice) {
    }

    fun getParticipants(): Set<Participant<*>> {
        return this.participants
    }
}