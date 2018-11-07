package com.dickow.chortlin.core.test.validation

import com.dickow.chortlin.core.ast.validation.ASTValidator
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.exceptions.InvalidASTException
import com.dickow.chortlin.core.test.shared.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class ASTValidationTests {

    @Test
    fun `check that validation fails for invalid ast end configuration`() {
        val choreography = Choreography.builder()
                .returnFrom(participant(A::class.java, "receive"), "error")
                .end()
        assertFailsWith(InvalidASTException::class) { choreography.runVisitor(ASTValidator()) }
    }

    @Test
    fun `check that validation accepts a valid ast`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(A::class.java, "receive"), "valid")
                .end()
        choreography.runVisitor(ASTValidator())
    }

    @Test
    @Suppress("CAST_NEVER_SUCCEEDS")
    fun `check that validator fails for paths without end`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(A::class.java, "receive"), "valid")
        assertFailsWith(ClassCastException::class) { choreography as Choreography }
    }

    @Test
    fun `check that parallel choreography without end throws an error`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(ParallelClassA::class.java, "method1"), "A:1")
                .parallel { c ->
                    c
                            .foundMessage(participant(ParallelClassB::class.java, "method1"), "B:1").end()
                }
                .end()
        choreography.runVisitor(ASTValidator())
    }

    @Test
    fun `ensure valid parallel choreography is accepted by validator`() {
        val choreography = Choreography.builder()
                .foundMessage(participant(ParallelClassA::class.java, "method1"), "A:1")
                .parallel { c ->
                    c
                            .interaction(
                                    participant(ParallelClassB::class.java, "method1"),
                                    participant(ParallelClassB::class.java, "method2"),
                                    "B:1 -> B:2")
                            .end()
                }
                .interaction(
                        participant(ParallelClassC::class.java, "method1"),
                        participant(ParallelClassC::class.java, "method2"),
                        "C:1 -> C:2")
                .end()
        choreography.runVisitor(ASTValidator())
    }

    @Test
    @Disabled
    fun `check that error is thrown for choices with similar start events`() {
        val choreography = Choreography.builder()
                .choice({ c -> c.foundMessage(participant(ChoiceClassA::class.java, "method1"), "receive on A").end() },
                        { c -> c.foundMessage(participant(ChoiceClassA::class.java, "method1"), "receive on A").end() })

        assertFailsWith(InvalidASTException::class) { choreography.runVisitor(ASTValidator()) }
    }
}