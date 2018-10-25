package com.dickow.chortlin.core.validation

import com.dickow.chortlin.core.shared.Scope
import java.util.function.Predicate

class ValidationScope<ASTNode> : Scope<ASTNode>() {
    fun hasOpen(predicate: Predicate<ASTNode>): Boolean {
        return scopes.any { any -> predicate.test(any) }
    }
}