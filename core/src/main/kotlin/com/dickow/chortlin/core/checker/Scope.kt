package com.dickow.chortlin.core.checker

import java.util.*

class Scope<T> {
    private var outerScope: T? = null
    private val currentScope: Stack<T> = Stack()

    fun beginNewScope(scope: T) {
        if (outerScope == null) {
            outerScope = scope
        }
        currentScope.add(scope)
    }

    fun exitScope() {
        currentScope.pop()
    }

    fun getCurrentScope(): T? {
        return if (currentScope.isEmpty()) {
            null
        } else {
            currentScope.peek()
        }
    }

    fun isInScope(): Boolean {
        return currentScope.isNotEmpty()
    }

    fun getOuterScope(): T? {
        return outerScope
    }

    fun hasOuterScope(): Boolean {
        return outerScope != null
    }
}