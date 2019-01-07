package com.dickow.chortlin.checker.choreography.validation

import com.dickow.chortlin.checker.ast.ASTVisitor
import com.dickow.chortlin.checker.ast.types.*
import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.checker.choreography.participant.ObservableParticipant
import com.dickow.chortlin.checker.correlation.Correlation
import com.dickow.chortlin.shared.exceptions.InvalidChoreographyException

class ChoreographyValidation(private val choreography: Choreography) : ASTVisitor {
    override fun visitEnd(astNode: End) {}

    override fun visitInteraction(astNode: Interaction) {
        checkNode(astNode, astNode.receiver)
    }

    override fun visitReturnFrom(astNode: ReturnFrom) {
        checkNode(astNode, astNode.participant)
    }

    override fun visitChoice(astNode: Choice) {
        throw InvalidChoreographyException("Unexpected choice node encountered")
    }

    private fun validFirstNode(correlation: Correlation) = correlation.hasInputFunctions()
    private fun isFirstNode(astNode: ASTNode) = astNode.previous == null

    private fun checkNode(astNode: ASTNode, participant: ObservableParticipant) {
        val correlation = choreography.getCorrelation(participant)
        if (correlation == null) {
            throw InvalidChoreographyException("Encountered the node: $astNode without a correlation defined.")
        } else {
            if (isFirstNode(astNode) && !validFirstNode(correlation)) {
                throw InvalidChoreographyException("Encountered an initial node: $astNode without any addition correlation functions.")
            }
            astNode.next!!.accept(this)
        }
    }
}