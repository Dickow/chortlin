package com.dickow.chortlin.checker.ast.types.placeholder

import com.dickow.chortlin.checker.ast.ASTBuilder
import com.dickow.chortlin.checker.ast.ASTVisitor
import com.dickow.chortlin.checker.ast.Label
import com.dickow.chortlin.checker.ast.types.*
import com.dickow.chortlin.checker.checker.result.CheckResult
import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.checker.choreography.participant.ObservableParticipant
import com.dickow.chortlin.checker.choreography.participant.Participant
import com.dickow.chortlin.shared.exceptions.InvalidASTException
import com.dickow.chortlin.checker.trace.Trace

class EmptyAST : ASTNode(null, null), Placeholder {
    override fun satisfy(trace: Trace): CheckResult {
        throw InvalidASTException("You have not configured anything for your choreography, please configure something before creating the checker.")
    }

    override fun accept(visitor: ASTVisitor) {
        throw InvalidASTException("Attempting to call visit on a Empty AST, this is not valid. " +
                "You probably asked for a builder and forgot to configure the choreography.")
    }

    override fun choice(vararg possiblePaths: (ASTBuilder) -> Choreography): Choreography {
        return Choreography(Choice(possiblePaths.map { it(Choreography.builder()) }, null))
    }

    override fun returnFrom(receiver: ObservableParticipant, label: String): ASTBuilder {
        return ReturnFrom(receiver, Label(label), null, null)
    }

    override fun interaction(sender: Participant, receiver: ObservableParticipant, label: String): ASTBuilder {
        return Interaction(sender, receiver, Label(label), null, null)
    }

    override fun end(): Choreography {
        throw InvalidASTException("You must configure a valid choreography before ending. Empty choreographies do nothing!")
    }
}