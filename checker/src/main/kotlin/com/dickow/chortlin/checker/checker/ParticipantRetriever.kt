package com.dickow.chortlin.checker.checker

import com.dickow.chortlin.checker.ast.ASTVisitor
import com.dickow.chortlin.checker.ast.types.Choice
import com.dickow.chortlin.checker.ast.types.End
import com.dickow.chortlin.checker.ast.types.Interaction
import com.dickow.chortlin.checker.ast.types.ReturnFrom
import com.dickow.chortlin.checker.choreography.participant.ObservableParticipant
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

    override fun visitChoice(astNode: Choice) {
        astNode.possiblePaths.forEach { node -> node.accept(this) }
    }

    fun getParticipants(): Set<ObservableParticipant> {
        return this.participants
    }
}