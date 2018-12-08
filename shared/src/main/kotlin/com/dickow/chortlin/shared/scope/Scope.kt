package com.dickow.chortlin.shared.scope

import java.util.*

open class Scope<T> {
    private var outerScope: T? = null
    protected val scopes: Stack<T> = Stack()

    fun beginNewScope(scope: T) {
        if (outerScope == null) {
            outerScope = scope
        }
        scopes.add(scope)
    }

    fun exitScope() {
        scopes.pop()
    }

    fun getCurrentScope(): T? {
        return if (scopes.isEmpty()) {
            null
        } else {
            scopes.peek()
        }
    }

    fun isInScope(): Boolean {
        return scopes.isNotEmpty()
    }

    fun hasOuterScope(): Boolean {
        return outerScope != null
    }

    fun getOuterScope(): T? {
        return outerScope
    }
}