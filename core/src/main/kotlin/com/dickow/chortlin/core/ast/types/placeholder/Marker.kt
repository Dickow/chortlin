package com.dickow.chortlin.core.ast.types.placeholder

import com.dickow.chortlin.core.ast.ASTVisitor
import com.dickow.chortlin.core.ast.Label
import com.dickow.chortlin.core.ast.types.*
import com.dickow.chortlin.core.checker.result.CheckResult
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.ChoreographyBuilder
import com.dickow.chortlin.core.choreography.participant.Participant
import com.dickow.chortlin.core.exceptions.InvalidASTException
import com.dickow.chortlin.core.trace.Trace

class Marker : ASTNode(null, null), Placeholder {
    override fun satisfy(trace: Trace): CheckResult {
        throw InvalidASTException("You have not configured anything for your choreography, please configure something before creating the checker.")
    }

    override fun accept(visitor: ASTVisitor) {
        throw InvalidASTException("Attempting to call visit on a Marker, this is not valid. " +
                "You probably asked for a builder and forgot to configure the choreography.")
    }

    override fun choice(vararg possiblePaths: (ChoreographyBuilder) -> Choreography): Choreography {
        return Choreography(Choice(possiblePaths.map { it(Choreography.builder()) }, null))
    }

    override fun parallel(path: (ChoreographyBuilder) -> Choreography): ChoreographyBuilder {
        return Parallel(path(Choreography.builder()), null, null)
    }

    override fun <C> returnFrom(receiver: Participant<C>, label: String): ChoreographyBuilder {
        return ReturnFrom(receiver, Label(label), null, null)
    }

    override fun <C> foundMessage(receiver: Participant<C>, label: String): ChoreographyBuilder {
        return FoundMessage(receiver, Label(label), null, null)
    }

    override fun <C1, C2> interaction(sender: Participant<C1>, receiver: Participant<C2>, label: String): ChoreographyBuilder {
        return Interaction(sender, receiver, Label(label), null, null)
    }

    override fun end(): Choreography {
        throw InvalidASTException("You must configure a valid choreography before ending. Empty choreographies do nothing!")
    }
}