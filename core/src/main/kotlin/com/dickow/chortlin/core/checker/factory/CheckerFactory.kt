package com.dickow.chortlin.core.checker.factory

import com.dickow.chortlin.core.ast.validation.ASTValidator
import com.dickow.chortlin.core.checker.ChoreographyChecker
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.validation.ChoreographyValidation

object CheckerFactory {
    fun createChecker(choreography: Choreography): ChoreographyChecker {
        choreography.runVisitor(ASTValidator())
        choreography.runVisitor(ChoreographyValidation(choreography))
        return ChoreographyChecker(choreography)
    }
}