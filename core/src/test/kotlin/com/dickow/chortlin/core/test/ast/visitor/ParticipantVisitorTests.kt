package com.dickow.chortlin.core.test.ast.visitor

import com.dickow.chortlin.core.checker.ParticipantRetriever
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.external
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.choreography.participant.observation.ObservableFactory
import com.dickow.chortlin.core.test.shared.FirstClass
import com.dickow.chortlin.core.test.shared.SecondClass
import com.dickow.chortlin.core.test.shared.ThirdClass
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ParticipantVisitorTests {

    @Test
    fun `check that found participants are as expected`() {
        val external = external("unknown sender")
        val first = participant(FirstClass::class.java) // "first", FirstClass::first
        val second = participant(SecondClass::class.java) // "second", SecondClass::second
        val third = participant(ThirdClass::class.java) // "third", ThirdClass::third

        val choreography = Choreography.builder()
                .interaction(external, first.onMethod("first"), "#1")
                .interaction(first, second.onMethod("second"), "#2")
                .interaction(second, third.onMethod("third"), "#3")
                .returnFrom(third.onMethod("third"), "return #3")
                .end()

        val participantRetriever = ParticipantRetriever()
        choreography.runVisitor(participantRetriever)
        val expected = setOf(
                ObservableFactory.observable(first, first.onMethod("first")),
                ObservableFactory.observable(second, second.onMethod("second")),
                ObservableFactory.observable(third, third.onMethod("third"))
        )
        assertEquals(expected, participantRetriever.getParticipants())
    }
}