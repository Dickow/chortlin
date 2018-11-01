package com.dickow.chortlin.core.ast.validation

import com.dickow.chortlin.core.shared.Scope
import java.util.function.Predicate

class ValidationScope<T> : Scope<T>() {
    fun hasOpen(predicate: Predicate<T>): Boolean {
        return scopes.any { any -> predicate.test(any) }
    }
}