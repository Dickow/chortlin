package com.dickow.chortlin.core.ast.types

import com.dickow.chortlin.core.ast.Label
import com.dickow.chortlin.core.ast.exception.InvalidASTException
import com.dickow.chortlin.core.ast.participant.Participant
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.ChoreographyBuilder

class Marker : ASTNode(null, null) {
    override fun build(): Choreography {
        throw InvalidASTException("You must configure a valid choreography before calling build")
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