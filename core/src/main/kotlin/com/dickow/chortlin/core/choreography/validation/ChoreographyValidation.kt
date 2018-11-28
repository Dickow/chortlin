package com.dickow.chortlin.core.choreography.validation

import com.dickow.chortlin.core.ast.ASTVisitor
import com.dickow.chortlin.core.ast.types.*
import com.dickow.chortlin.core.choreography.participant.ObservableParticipant
import com.dickow.chortlin.core.correlation.Correlation
import com.dickow.chortlin.core.correlation.CorrelationSet
import com.dickow.chortlin.core.exceptions.InvalidChoreographyException

class ChoreographyValidation(private val correlationSet: CorrelationSet) : ASTVisitor {
    override fun visitEnd(astNode: End) {}

    override fun <T> visitInteraction(astNode: Interaction<T>) {
        checkNode(astNode, astNode.receiver)
    }

    override fun <T> visitReturnFrom(astNode: ReturnFrom<T>) {
        checkNode(astNode, astNode.participant)
    }

    override fun visitParallel(astNode: Parallel) {
        throw InvalidChoreographyException("Unexpected parallel node encountered")
    }

    override fun visitChoice(astNode: Choice) {
        throw InvalidChoreographyException("Unexpected choice node encountered")
    }

    private fun validFirstNode(correlation: Correlation) = correlation.addFunctions.isNotEmpty()
    private fun isFirstNode(astNode: ASTNode) = astNode.previous == null

    private fun checkNode(astNode: ASTNode, participant: ObservableParticipant<*>) {
        val correlation = correlationSet.get(participant)
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