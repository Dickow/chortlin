package com.dickow.chortlin.core.test.validation

import com.dickow.chortlin.core.ast.exception.InvalidASTException
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.test.shared.A
import com.dickow.chortlin.core.validation.ChoreographyValidator
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class ASTValidationTests {

    @Test
    fun `check that validation fails for invalid ast end configuration`() {
        val choreography = Choreography.builder()
                .foundMessageReturn(participant(A::class.java, "receive"), "error")
                .end()
                .build()
        assertFailsWith(InvalidASTException::class) { choreography.runVisitor(ChoreographyValidator()) }
    }

    @Test
    fun `check that validation accepts a valid ast`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(A::class.java, "receive"), "valid")
                .end()
                .build()
        choreography.runVisitor(ChoreographyValidator())
    }

    @Test
    fun `check that validator fails for paths without end`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(A::class.java, "receive"), "valid")
                .build()
        assertFailsWith(InvalidASTException::class) { choreography.runVisitor(ChoreographyValidator()) }
    }
}