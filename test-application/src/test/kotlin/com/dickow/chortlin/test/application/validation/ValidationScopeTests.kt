package com.dickow.chortlin.test.application.validation

import com.dickow.chortlin.checker.ast.validation.ValidationScope
import java.util.function.Predicate
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ValidationScopeTests {
    private val scope = ValidationScope<String>()

    @Test
    fun `check that predicate works on scope`() {
        scope.beginNewScope("Hello")
        scope.beginNewScope("World")
        scope.beginNewScope("!")

        assertTrue(scope.hasOpen(Predicate { v -> v == "Hello" }))
        assertFalse(scope.hasOpen(Predicate { v -> v == "Goodbye" }))
    }
}