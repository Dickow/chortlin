package com.dickow.chortlin.core.instrumentation.strategy.factory

import com.dickow.chortlin.core.checker.OnlineChecker
import com.dickow.chortlin.core.checker.session.InMemorySessionManager
import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.instrumentation.ASTInstrumentation
import com.dickow.chortlin.core.instrumentation.ByteBuddyInstrumentation
import com.dickow.chortlin.core.instrumentation.strategy.CheckInMemory
import com.dickow.chortlin.core.instrumentation.strategy.InterceptStrategy
import com.dickow.chortlin.core.instrumentation.strategy.StoreInMemory

object StrategyFactory {
    fun createInMemoryChecker(choreographies: List<Choreography>, failFast: Boolean = true): InterceptStrategy {
        val instrumentation = ASTInstrumentation(ByteBuddyInstrumentation)
        for (choreography in choreographies) {
            choreography.runVisitor(instrumentation)
        }
        return CheckInMemory(OnlineChecker(InMemorySessionManager(choreographies)), failFast)
    }

    fun createStoreInMemory(): InterceptStrategy {
        return StoreInMemory()
    }
}