package com.dickow.chortlin.checker.checker.factory

import com.dickow.chortlin.checker.ast.validation.ASTValidator
import com.dickow.chortlin.checker.checker.ChoreographyChecker
import com.dickow.chortlin.checker.checker.OnlineChecker
import com.dickow.chortlin.checker.checker.session.InMemorySessionManager
import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.checker.choreography.validation.ChoreographyValidation

object OnlineCheckerFactory {

    @JvmStatic
    fun createOnlineChecker(choreographies: List<Choreography>): ChoreographyChecker {
        choreographies.forEach(this::validate)
        return OnlineChecker(InMemorySessionManager(choreographies))
    }

    @JvmStatic
    private fun validate(choreography: Choreography) {
        choreography.runVisitor(ASTValidator())
        choreography.runVisitor(ChoreographyValidation(choreography))
    }
}