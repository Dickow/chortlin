package com.dickow.chortlin.core.checker

import com.dickow.chortlin.core.ast.validation.ASTValidator
import com.dickow.chortlin.core.checker.result.CheckResult
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.trace.Trace

class ChoreographyChecker(private val choreography: Choreography) {

    init {
        choreography.start.accept(ASTValidator())
    }

    fun check(trace : Trace) : CheckResult {
        trace.markAllNonConsumed()
        return choreography.start.satisfy(trace)
    }
}