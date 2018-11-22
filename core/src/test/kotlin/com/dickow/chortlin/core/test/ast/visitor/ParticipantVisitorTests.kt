package com.dickow.chortlin.core.test.ast.visitor

import com.dickow.chortlin.core.checker.ParticipantRetriever
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.external
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.test.shared.FirstClass
import com.dickow.chortlin.core.test.shared.SecondClass
import com.dickow.chortlin.core.test.shared.ThirdClass
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ParticipantVisitorTests {

    @Test
    fun `check that found participants are as expected`() {
        val external = external("unknown sender")
        val first = participant(FirstClass::class.java, "first")
        val second = participant(SecondClass::class.java, "second")
        val third = participant(ThirdClass::class.java, "third")

        val choreography = Choreography.builder()
                .interaction(external, first, "#1")
                .interaction(first.nonObservable, second, "#2")
                .interaction(second.nonObservable, third, "#3")
                .returnFrom(third, "return #3")
                .end()

        val participantRetriever = ParticipantRetriever()
        choreography.runVisitor(participantRetriever)
        val expected = setOf(
                participant(FirstClass::class.java, "first"),
                participant(SecondClass::class.java, "second"),
                participant(ThirdClass::class.java, "third")
        )
        assertEquals(expected, participantRetriever.getParticipants())
    }
}