package com.dickow.chortlin.core.test.ast

import com.dickow.chortlin.core.ast.Label
import com.dickow.chortlin.core.ast.types.*
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.test.shared.*
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

        val found = FoundMessage(
                participant(A::class.java, "receive"),
                Label("receive"), null, null)
        val interaction = Interaction(
                participant(A::class.java, "b"),
                participant(B::class.java, "b"),
                Label("delegate processing"),
                found, null)
        val end = End(interaction)
        found.next = interaction
        interaction.next = end

        val expected = Choreography(found)
        assertEquals(expected, choreography)
    }

    @Test
    fun `build single found msg element ast`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(A::class.java, "receive"), "receive").end()
        val found = FoundMessage(
                participant(A::class.java, "receive"),
                Label("receive"), null, null)
        val end = End(found)
        found.next = end
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
                }
                .foundMessage(participant(B::class.java, "b"), "b receiver")
                .end()
        val found = FoundMessage(participant(A::class.java, "receive"), Label("receive"), null, null)
        val parallelFound = FoundMessage(participant(A::class.java, "b"), Label("receive in parallel"), found, null)
        parallelFound.next = End(parallelFound)

        val parallel = Parallel(Choreography(parallelFound), found, null)
        val finalFound = FoundMessage(participant(B::class.java, "b"), Label("b receiver"), parallel, null)
        val end = End(finalFound)
        found.next = parallel
        parallel.next = finalFound
        finalFound.next = end

        val expected = Choreography(found)
        assertEquals(expected, choreography)
    }

    @Test
    fun `create fully parallel choreography`() {
        val choreography = Choreography.builder()
                .parallel { c -> c.foundMessage(participant(ParallelClassA::class.java, "method1"), "a receive").end() }
                .foundMessage(participant(ParallelClassB::class.java, "method1"), "b receive")
                .end()

        val parallelFound = FoundMessage(participant(ParallelClassA::class.java, "method1"), Label("a receive"), null, null)
        val parallelEnd = End(parallelFound)
        parallelFound.next = parallelEnd
        val parallel = Parallel(Choreography(parallelFound), null, null)
        val found = FoundMessage(participant(ParallelClassB::class.java, "method1"), Label("b receive"), parallel, null)
        val end = End(found)
        found.next = end
        parallel.next = found

        val expected = Choreography(parallel)
        assertEquals(expected, choreography)
    }

    @Test
    fun `create simplest choice choreography`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(ChoiceClassA::class.java, "method1"), "receive on A")
                .choice({ c -> c.foundMessage(participant(ChoiceClassB::class.java, "method1"), "call B").end() },
                        { c -> c.foundMessage(participant(ChoiceClassC::class.java, "method1"), "call C").end() })

        val foundForA = FoundMessage(participant(ChoiceClassA::class.java, "method1"), Label("receive on A"), null, null)
        val foundForB = FoundMessage(participant(ChoiceClassB::class.java, "method1"), Label("call B"), null, null)
        val endForFoundB = End(foundForB)
        val foundForC = FoundMessage(participant(ChoiceClassC::class.java, "method1"), Label("call C"), null, null)
        val endForFoundC = End(foundForC)
        val choice = Choice(listOf(Choreography(foundForB), Choreography(foundForC)), foundForA)

        foundForA.next = choice
        foundForB.next = endForFoundB
        foundForC.next = endForFoundC
        val expected = Choreography(foundForA)

        assertEquals(expected, choreography)
    }

    @Test
    fun `create parallel and choice choreography`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(A::class.java, "receive"), "A receives")
                .parallel { c ->
                    c.choice(
                            { b ->
                                b.foundMessage(participant(ChoiceClassA::class.java, "method1"), "A finds message")
                                        .end()
                            },
                            { b ->
                                b.foundMessage(participant(ChoiceClassB::class.java, "method1"), "B finds message")
                                        .end()
                            })
                }
                .interaction(
                        participant(ParallelClassC::class.java, "method1"),
                        participant(ParallelClassC::class.java, "method2"), "C:1 -> C:2")
                .end()

        val foundA = FoundMessage(participant(A::class.java, "receive"), Label("A receives"), null, null)
        val foundChoiceA = FoundMessage(participant(ChoiceClassA::class.java, "method1"), Label("A finds message"), null, null)
        val choiceAEnd = End(foundChoiceA)
        val foundChoiceB = FoundMessage(participant(ChoiceClassB::class.java, "method1"), Label("B finds message"), null, null)
        val choiceBEnd = End(foundChoiceB)
        val interaction = Interaction(participant(ParallelClassC::class.java, "method1"),
                participant(ParallelClassC::class.java, "method2"), Label("C:1 -> C:2"), null, null)
        val end = End(interaction)
        val choice = Choice(listOf(Choreography(foundChoiceA), Choreography(foundChoiceB)), null)
        val parallelNode = Parallel(Choreography(choice), foundA, null)

        foundChoiceA.next = choiceAEnd
        foundChoiceB.next = choiceBEnd
        interaction.next = end
        parallelNode.next = interaction
        foundA.next = parallelNode

        val expected = Choreography(foundA)
        assertEquals(expected, choreography)
    }
}