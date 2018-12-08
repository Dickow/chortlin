package com.dickow.chortlin.core

import com.dickow.chortlin.checker.checker.OnlineChecker
import com.dickow.chortlin.checker.checker.session.InMemorySessionManager
import com.dickow.chortlin.checker.choreography.Choreography
import com.dickow.chortlin.interception.instrumentation.ByteBuddyInstrumentation
import com.dickow.chortlin.interception.instrumentation.strategy.InterceptStrategy

object InMemoryStrategyFactory {
    @JvmStatic
    fun createInMemoryChecker(choreographies: List<Choreography>, failFast: Boolean = true): InterceptStrategy {
        val instrumentation = ASTInstrumentation(ByteBuddyInstrumentation)
        for (choreography in choreographies) {
            choreography.runVisitor(instrumentation)
        }
        return CheckInMemory(OnlineChecker(InMemorySessionManager(choreographies)), failFast)
    }
}