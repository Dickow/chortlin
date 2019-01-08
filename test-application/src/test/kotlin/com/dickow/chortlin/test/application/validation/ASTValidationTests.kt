package com.dickow.chortlin.test.application.validation

import com.dickow.chortlin.checker.ast.types.factory.TypeFactory.choice
import com.dickow.chortlin.checker.ast.types.factory.TypeFactory.interaction
import com.dickow.chortlin.checker.ast.validation.ASTValidator
import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.external
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.shared.exceptions.InvalidASTException
import com.dickow.chortlin.test.application.shared.A
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class ASTValidationTests {

    private val external = external("External")

    @Test
    fun `check that validation accepts a valid ast`() {
        val choreography =
                interaction(external, participant(A::class.java).onMethod("receive"), "valid")
                .end()
        choreography.runVisitor(ASTValidator())
    }

    @Test
    @Suppress("CAST_NEVER_SUCCEEDS")
    fun `check that validator fails for paths without end`() {
        val choreography =
                interaction(external, participant(A::class.java).onMethod("receive"), "invalid")
        assertFailsWith(ClassCastException::class) { choreography as Choreography }
    }

    @Test
    fun `check that choice with no branches is invalid`() {
        val choreography = choice()
        assertFailsWith(InvalidASTException::class) { choreography.runVisitor(ASTValidator()) }
    }

    @Test
    fun `ensure that valid choice is accepted by validation`() {
        val choreography = choice(
                interaction(external, participant(A::class.java).onMethod("receive"), "invalid")
                        .end()
        )
        choreography.runVisitor(ASTValidator())
    }
}