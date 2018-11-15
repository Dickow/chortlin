package com.dickow.chortlin.core.test.ast.visitor

import com.dickow.chortlin.core.checker.ParticipantRetriever
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.test.shared.FirstClass
import com.dickow.chortlin.core.test.shared.SecondClass
import com.dickow.chortlin.core.test.shared.ThirdClass
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ParticipantVisitorTests {

    @Test
    fun `check that found participants are as expected`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(FirstClass::class.java, "first"), "#1")
                .interaction(participant(SecondClass::class.java, "second"),
                        participant(ThirdClass::class.java, "third"),
                        "#2")
                .returnFrom(participant(ThirdClass::class.java, "third"), "return #2(2)")
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