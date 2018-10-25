package com.dickow.chortlin.core.ast.types

import com.dickow.chortlin.core.ast.ASTVisitor
import com.dickow.chortlin.core.ast.Label
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.ChoreographyBuilder
import com.dickow.chortlin.core.choreography.participant.Participant
import com.dickow.chortlin.core.exceptions.InvalidASTException

class Marker : ASTNode(null, null) {
    override fun accept(visitor: ASTVisitor) {
        throw InvalidASTException("Attempting to call visit on a Marker, this is not valid. " +
                "You probably asked for a builder and forgot to configure the choreography.")
    }

    override fun build(): Choreography {
        throw InvalidASTException("You must configure a valid choreography before calling build")
    }

    override fun <C> foundMessageReturn(receiver: Participant<C>, label: String): ChoreographyBuilder {
        return FoundMessageReturn(receiver, Label(label), null, null)
    }

    override fun <C1, C2> interactionReturn(sender: Participant<C1>, receiver: Participant<C2>, label: String): ChoreographyBuilder {
        return InteractionReturn(sender, receiver, Label(label), null, null)
    }

    override fun <C> foundMessage(receiver: Participant<C>, label: String): ChoreographyBuilder {
        return FoundMessage(receiver, Label(label), null, null)
    }

    override fun <C1, C2> interaction(sender: Participant<C1>, receiver: Participant<C2>, label: String): ChoreographyBuilder {
        return Interaction(sender, receiver, Label(label), null, null)
    }

    override fun end(): ChoreographyBuilder {
        return End(null, null)
    }
}