package com.dickow.chortlin.core.test.ast

import com.dickow.chortlin.core.ast.Label
import com.dickow.chortlin.core.ast.types.End
import com.dickow.chortlin.core.ast.types.Interaction
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.NonObservableParticipant
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.choreography.participant.entity.ExternalEntity
import com.dickow.chortlin.core.choreography.participant.entity.InternalEntity
import com.dickow.chortlin.core.test.shared.A
import com.dickow.chortlin.core.test.shared.B
import kotlin.test.Test
import kotlin.test.assertEquals

class ASTBuilderTests {

    private val external = ParticipantFactory.external("external")
    private val aReceive = participant(A::class.java, "receive", A::receive)
    private val aB = participant(A::class.java, "b", A::b)
    private val bB = participant(B::class.java, "b", B::b)

    @Test
    fun `build simple receive then interaction choreography`() {
        val choreography = Choreography.builder()
                .interaction(external, aReceive, "receive")
                .interaction(aReceive.nonObservable(), aB, "call A#b")
                .interaction(aB.nonObservable(), bB, "Invoke B#b")
                .end()

        val interaction1 = Interaction(
                NonObservableParticipant(ExternalEntity("external")),
                participant(A::class.java, "receive", A::receive),
                Label("receive"),
                null,
                null
        )

        val interaction2 = Interaction(
                NonObservableParticipant(InternalEntity(A::class.java)),
                participant(A::class.java, "b", A::b),
                Label("call A#b"),
                interaction1,
                null
        )

        val interaction3 = Interaction(
                NonObservableParticipant(InternalEntity(A::class.java)),
                participant(B::class.java, "b", B::b),
                Label("Invoke B#b"),
                interaction2,
                null
        )

        val end = End(interaction3)
        interaction1.next = interaction2
        interaction2.next = interaction3
        interaction3.next = end

        val expected = Choreography(interaction1)
        assertEquals(expected, choreography)
    }

    @Test
    fun `build single found msg element ast`() {
        val choreography = Choreography.builder()
                .interaction(external, aReceive, "receive")
                .end()

        val interaction = Interaction(
                NonObservableParticipant(ExternalEntity("external")),
                participant(A::class.java, "receive", A::receive),
                Label("receive"),
                null,
                null
        )
        val end = End(interaction)
        interaction.next = end
        val expected = Choreography(interaction)
        assertEquals(expected, choreography)
    }
}