package com.dickow.chortlin.core.test.ast

import com.dickow.chortlin.checker.ast.Label
import com.dickow.chortlin.checker.ast.types.End
import com.dickow.chortlin.checker.ast.types.Interaction
import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.checker.choreography.participant.ExternalParticipant
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.test.shared.A
import com.dickow.chortlin.core.test.shared.B
import com.dickow.chortlin.shared.observation.ObservableParticipant
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ASTBuilderTests {

    private val external = ParticipantFactory.external("external")
    private val a = participant(A::class.java) // "receive", A::receive
    private val b = participant(B::class.java) // "b", B::b

    @Test
    fun `build simple receive then interaction choreography`() {
        val choreography = Choreography.builder()
                .interaction(external, a.onMethod("receive"), "receive")
                .interaction(a, a.onMethod("b"), "call A#b")
                .interaction(a, b.onMethod("b"), "Invoke B#b")
                .end()

        val interaction1 = Interaction(
                ExternalParticipant("external"),
                ObservableParticipant(a.clazz, a.onMethod("receive").jvmMethod),
                Label("receive"),
                null,
                null
        )

        val interaction2 = Interaction(
                a,
                ObservableParticipant(a.clazz, a.onMethod("b").jvmMethod),
                Label("call A#b"),
                interaction1,
                null
        )

        val interaction3 = Interaction(
                a,
                ObservableParticipant(b.clazz, b.onMethod("b").jvmMethod),
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
                .interaction(external, a.onMethod("receive"), "receive")
                .end()

        val interaction = Interaction(
                ExternalParticipant("external"),
                ObservableParticipant(a.clazz, a.onMethod("receive").jvmMethod),
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