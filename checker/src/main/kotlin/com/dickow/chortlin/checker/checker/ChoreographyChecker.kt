package com.dickow.chortlin.checker.checker

import com.dickow.chortlin.checker.ast.validation.ASTValidator
import com.dickow.chortlin.checker.checker.result.CheckResult
import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.shared.trace.Trace

class ChoreographyChecker(private val choreography: Choreography) {

    init {
        choreography.start.accept(ASTValidator())
    }

    fun check(trace : Trace) : CheckResult {
        trace.markAllNonConsumed()
        return choreography.start.satisfy(trace)
    }
}