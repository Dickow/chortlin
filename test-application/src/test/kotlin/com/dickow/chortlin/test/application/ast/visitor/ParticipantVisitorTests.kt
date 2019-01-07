package com.dickow.chortlin.test.application.ast.visitor

import com.dickow.chortlin.checker.ast.types.factory.TypeFactory.interaction
import com.dickow.chortlin.checker.checker.ParticipantRetriever
import com.dickow.chortlin.checker.choreography.participant.ObservableParticipant
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.external
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.test.application.shared.FirstClass
import com.dickow.chortlin.test.application.shared.SecondClass
import com.dickow.chortlin.test.application.shared.ThirdClass
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ParticipantVisitorTests {

    @Test
    fun `check that found participants are as expected`() {
        val external = external("unknown sender")
        val first = participant(FirstClass::class.java) // "first", FirstClass::first
        val second = participant(SecondClass::class.java) // "second", SecondClass::second
        val third = participant(ThirdClass::class.java) // "third", ThirdClass::third

        val choreography =
                interaction(external, first.onMethod("first"), "#1")
                .interaction(first, second.onMethod("second"), "#2")
                .interaction(second, third.onMethod("third"), "#3")
                .returnFrom(third.onMethod("third"), "return #3")
                .end()

        val participantRetriever = ParticipantRetriever()
        choreography.runVisitor(participantRetriever)
        val expected = setOf(
                ObservableParticipant(first.identifier, "first"),
                ObservableParticipant(second.identifier, "second"),
                ObservableParticipant(third.identifier, "third")
        )
        assertEquals(expected, participantRetriever.getParticipants())
    }
}