package com.dickow.chortlin.checker.test

import com.dickow.chortlin.checker.ast.validation.ASTValidator
import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.checker.choreography.participant.ParticipantFactory
import com.dickow.chortlin.checker.correlation.factory.CorrelationFactory.correlation
import com.dickow.chortlin.checker.correlation.factory.CorrelationFactory.defineCorrelation
import com.dickow.chortlin.checker.test.shared.Annotated1
import com.dickow.chortlin.checker.test.shared.Annotated2
import com.dickow.chortlin.shared.exceptions.InvalidASTException
import kotlin.test.Test
import kotlin.test.assertFailsWith

class AnnotationTypeCheckerTests {

    private val externalClient = ParticipantFactory.external("client")
    private val annotationClass1 = ParticipantFactory.participant(Annotated1::class.java)
    private val annotationClass2 = ParticipantFactory.participant(Annotated2::class.java)

    private val cdef = defineCorrelation()
            .add(correlation(annotationClass1.onMethod("annotatedMethod", Annotated1::annotatedMethod),
                    "in", { input: String -> input })
                    .extendFromInput("in", { input: String -> input })
                    .done())
            .add(correlation(annotationClass1.onMethod("nonAnnotatedMethod", Annotated1::nonAnnotatedMethod),
                    "in", { input: String -> input }).done())
            .add(correlation(annotationClass2.onMethod("invocationAnnotatedMethod", Annotated2::invocationAnnotatedMethod),
                    "in", { input: String -> input })
                    .extendFromInput("in", { input: String -> input })
                    .done())
            .add(correlation(annotationClass2.onMethod("returnAnnotatedMethod", Annotated2::returnAnnotatedMethod),
                    "in", { input: String -> input })
                    .extendFromInput("in", { input: String -> input })
                    .done())
            .finish()

    @Test
    fun `check that correctly annotated choreography passes check`() {
        val choreography = Choreography.builder()
                .interaction(externalClient, annotationClass1.onMethod("annotatedMethod"), "annotated1")
                .returnFrom(annotationClass1.onMethod("annotatedMethod"), "return")
                .end()
                .setCorrelation(cdef)
        choreography.runVisitor(ASTValidator())
    }

    @Test
    fun `check that choreography with missing annotations throws an error`() {
        val choreography = Choreography.builder()
                .interaction(externalClient, annotationClass1.onMethod("nonAnnotatedMethod"), "annotated1")
                .returnFrom(annotationClass1.onMethod("nonAnnotatedMethod"), "return")
                .end()
                .setCorrelation(cdef)
        assertFailsWith(InvalidASTException::class) { choreography.runVisitor(ASTValidator()) }
    }

    @Test
    fun `ensure that return from annotation type check is required when return from encountered`() {
        val choreography = Choreography.builder()
                .interaction(externalClient, annotationClass2.onMethod("invocationAnnotatedMethod"), "annotated2")
                .returnFrom(annotationClass2.onMethod("invocationAnnotatedMethod"), "return")
                .end()
                .setCorrelation(cdef)
        assertFailsWith(InvalidASTException::class) { choreography.runVisitor(ASTValidator()) }
    }

    @Test
    fun `ensure that invoke annotation type check is required when interaction is encountered`() {
        val choreography = Choreography.builder()
                .interaction(externalClient, annotationClass2.onMethod("returnAnnotatedMethod"), "annotated2")
                .returnFrom(annotationClass2.onMethod("returnAnnotatedMethod"), "return")
                .end()
                .setCorrelation(cdef)
        assertFailsWith(InvalidASTException::class) { choreography.runVisitor(ASTValidator()) }
    }
}