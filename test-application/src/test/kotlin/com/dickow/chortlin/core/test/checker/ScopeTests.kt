package com.dickow.chortlin.core.test.checker

import com.dickow.chortlin.shared.scope.Scope
import kotlin.test.*

class ScopeTests {

    @Test
    fun `check that scope is empty if no scope was started`() {
        val scope = Scope<Any>()
        assertFalse(scope.hasOuterScope())
        assertFalse(scope.isInScope())
    }

    @Test
    fun `check that scope registers beginning of scope correctly`() {
        val scope = Scope<Any>()
        scope.beginNewScope("")
        assertTrue(scope.isInScope())
        assertTrue(scope.hasOuterScope())
        assertNotNull(scope.getCurrentScope())
        assertNotNull(scope.getOuterScope())
    }

    @Test
    fun `check that inner scope is different from outer scope`() {
        val scope = Scope<Any>()
        scope.beginNewScope("")
        scope.beginNewScope(1)
        assertTrue(scope.isInScope())
        assertTrue(scope.hasOuterScope())
        assertNotNull(scope.getCurrentScope())
        assertNotNull(scope.getOuterScope())
        assertNotEquals(scope.getOuterScope(), scope.getCurrentScope())
    }

    @Test
    fun `check that exiting scope works`() {
        val scope = Scope<Any>()
        scope.beginNewScope("")
        scope.beginNewScope(1)
        scope.beginNewScope(2)
        assertTrue(scope.isInScope())
        assertTrue(scope.hasOuterScope())
        val innerScope = scope.getCurrentScope()
        scope.exitScope()
        assertNotEquals(scope.getCurrentScope(), innerScope)
    }
}