package com.dickow.chortlin.checker.ast.validation

import com.dickow.chortlin.shared.scope.Scope
import java.util.function.Predicate

class ValidationScope<T> : Scope<T>() {
    fun hasOpen(predicate: Predicate<T>): Boolean {
        return scopes.any { any -> predicate.test(any) }
    }
}