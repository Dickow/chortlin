package com.dickow.chortlin.core.checker

import com.dickow.chortlin.core.ast.ASTVisitor
import com.dickow.chortlin.core.ast.types.*
import com.dickow.chortlin.core.choreography.participant.observation.ObservableParticipant
import java.util.*

class ParticipantRetriever : ASTVisitor {
    private val participants = HashSet<ObservableParticipant>()

    override fun visitEnd(astNode: End) {
    }

    override fun visitInteraction(astNode: Interaction) {
        participants.add(astNode.receiver)
        astNode.next?.accept(this)
    }

    override fun visitReturnFrom(astNode: ReturnFrom) {
        participants.add(astNode.participant)
        astNode.next?.accept(this)
    }

    override fun visitParallel(astNode: Parallel) {
    }

    override fun visitChoice(astNode: Choice) {
    }

    fun getParticipants(): Set<ObservableParticipant> {
        return this.participants
    }
}