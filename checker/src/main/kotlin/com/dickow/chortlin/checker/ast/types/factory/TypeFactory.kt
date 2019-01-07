package com.dickow.chortlin.checker.ast.types.factory

import com.dickow.chortlin.checker.ast.ASTBuilder
import com.dickow.chortlin.checker.ast.Label
import com.dickow.chortlin.checker.ast.types.Choice
import com.dickow.chortlin.checker.ast.types.Interaction
import com.dickow.chortlin.checker.ast.types.ReturnFrom
import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.checker.choreography.participant.ObservableParticipant
import com.dickow.chortlin.checker.choreography.participant.Participant

object TypeFactory {
    @JvmStatic
    fun returnFrom(receiver: ObservableParticipant, label: String): ASTBuilder {
        return ReturnFrom(receiver, Label(label), null, null)
    }

    @JvmStatic
    fun interaction(sender: Participant, receiver: ObservableParticipant, label: String): ASTBuilder {
        return Interaction(sender, receiver, Label(label), null, null)
    }

    @JvmStatic
    fun choice(branches: List<Choreography>): Choreography {
        val astNodes = branches.map { c -> c.start }
        return Choreography(Choice(astNodes, null))
    }
}