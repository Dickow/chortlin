package com.dickow.chortlin.test.application.validation

import com.dickow.chortlin.checker.ast.validation.ASTValidator
import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory.external
import com.dickow.chortlin.test.application.shared.A
import com.dickow.chortlin.shared.exceptions.InvalidASTException
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class ASTValidationTests {

    private val external = external("External")

    @Test
    fun `check that validation fails for invalid ast end configuration`() {
        val choreography = Choreography.builder()
                .returnFrom(participant(A::class.java).onMethod("receive"), "error")
                .end()
        assertFailsWith(InvalidASTException::class) { choreography.runVisitor(ASTValidator()) }
    }

    @Test
    fun `check that validation accepts a valid ast`() {
        val choreography = Choreography.builder()
                .interaction(external, participant(A::class.java).onMethod("receive"), "valid")
                .end()
        choreography.runVisitor(ASTValidator())
    }

    @Test
    @Suppress("CAST_NEVER_SUCCEEDS")
    fun `check that validator fails for paths without end`() {
        val choreography = Choreography.builder()
                .interaction(external, participant(A::class.java).onMethod("receive"), "invalid")
        assertFailsWith(ClassCastException::class) { choreography as Choreography }
    }
}