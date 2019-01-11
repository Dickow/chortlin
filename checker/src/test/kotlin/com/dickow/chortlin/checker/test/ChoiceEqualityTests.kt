package com.dickow.chortlin.checker.test

import com.dickow.chortlin.checker.ast.types.factory.TypeFactory
import com.dickow.chortlin.checker.ast.types.factory.TypeFactory.interaction
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.external
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.participant
import kotlin.test.Test
import kotlin.test.assertEquals

class ChoiceEqualityTests {

    @Test
    fun `test that structurally equal choice nodes are equal`() {
        val choreographies1 = listOf(
                interaction(external("client"), participant("p").onMethod("m"), "a").end(),
                interaction(external("client"), participant("p1").onMethod("m1"), "a").end()
        )
        val choreographies2 = listOf(
                interaction(external("client"), participant("p").onMethod("m"), "a").end(),
                interaction(external("client"), participant("p1").onMethod("m1"), "a").end()
        )
        val choice1 = TypeFactory.choice(choreographies1[0], choreographies1[1])
        val choice2 = TypeFactory.choice(choreographies2[0], choreographies2[1])
        assertEquals(choice1, choice2)
    }
}