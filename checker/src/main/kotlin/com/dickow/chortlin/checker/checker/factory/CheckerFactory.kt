package com.dickow.chortlin.checker.checker.factory

import com.dickow.chortlin.checker.ast.validation.ASTValidator
import com.dickow.chortlin.checker.checker.ChoreographyChecker
import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.checker.choreography.validation.ChoreographyValidation

object CheckerFactory {
    fun createChecker(choreography: Choreography): ChoreographyChecker {
        choreography.runVisitor(ASTValidator())
        choreography.runVisitor(ChoreographyValidation(choreography))
        return ChoreographyChecker(choreography)
    }
}