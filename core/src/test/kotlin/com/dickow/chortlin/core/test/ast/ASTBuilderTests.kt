package com.dickow.chortlin.core.test.ast

import com.dickow.chortlin.core.ast.Label
import com.dickow.chortlin.core.ast.types.End
import com.dickow.chortlin.core.ast.types.FoundMessage
import com.dickow.chortlin.core.ast.types.Interaction
import com.dickow.chortlin.core.ast.types.Parallel
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.test.shared.A
import com.dickow.chortlin.core.test.shared.B
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

    @Test
    fun `build single element ast`() {
        val choreography = Choreography.builder().end().build()
        val end = End(null, null)
        val expected = Choreography(end)
        assertEquals(expected, choreography)
    }

    @Test
    fun `build single found msg element ast`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(A::class.java, "receive"), "receive")
                .build()
        val found = FoundMessage(
                participant(A::class.java, "receive"),
                Label("receive"), null, null)
        val expected = Choreography(found)
        assertEquals(expected, choreography)
    }

    @Test
    fun `construct ast with parallel node`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(A::class.java, "receive"), "receive")
                .parallel { c ->
                    c
                            .foundMessage(participant(A::class.java, "b"), "receive in parallel")
                            .end()
                            .build()
                }
                .foundMessage(participant(B::class.java, "b"), "b receiver")
                .end()
                .build()
        val found = FoundMessage(participant(A::class.java, "receive"), Label("receive"), null, null)
        val parallelFound = FoundMessage(participant(A::class.java, "b"), Label("receive in parallel"), found, null)
        parallelFound.next = End(parallelFound, null)

        val parallel = Parallel(Choreography(parallelFound), found, null)
        val finalFound = FoundMessage(participant(B::class.java, "b"), Label("b receiver"), parallel, null)
        val end = End(finalFound, null)
        found.next = parallel
        parallel.next = finalFound
        finalFound.next = end

        val expected = Choreography(found)
        assertEquals(expected, choreography)
    }
}