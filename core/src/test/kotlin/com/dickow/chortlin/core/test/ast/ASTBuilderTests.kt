package com.dickow.chortlin.core.test.ast

import com.dickow.chortlin.core.ast.Label
import com.dickow.chortlin.core.ast.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.ast.types.End
import com.dickow.chortlin.core.ast.types.FoundMessage
import com.dickow.chortlin.core.ast.types.Interaction
import com.dickow.chortlin.core.choreography.Choreography
import kotlin.test.Test
import kotlin.test.assertEquals

class ASTBuilderTests {

    @Test
    fun `build simple receive then interaction choreography`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(A::class.java, "receive"), "receive")
                .interaction(participant(A::class.java, "b"),
                        participant(B::class.java, "b"),
                        "delegate processing")
                .end()
                .build()

        val found = FoundMessage(
                participant(A::class.java, "receive"),
                Label("receive"), null, null)
        val interaction = Interaction(
                participant(A::class.java, "b"),
                participant(B::class.java, "b"),
                Label("delegate processing"),
                found, null)
        val end = End(interaction, null)
        found.next = interaction
        interaction.next = end

        val expected = Choreography(found)
        assertEquals(expected, choreography)
    }
}