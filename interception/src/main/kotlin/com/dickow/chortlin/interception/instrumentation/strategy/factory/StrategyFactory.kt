package com.dickow.chortlin.inteception.instrumentation.strategy.factory

import com.dickow.chortlin.inteception.instrumentation.strategy.InterceptStrategy
import com.dickow.chortlin.inteception.instrumentation.strategy.StoreInMemory


object StrategyFactory {
//    @JvmStatic
//    fun createInMemoryChecker(choreographies: List<Choreography>, failFast: Boolean = true): InterceptStrategy {
//        val instrumentation = ASTInstrumentation(ByteBuddyInstrumentation)
//        for (choreography in choreographies) {
//            choreography.runVisitor(instrumentation)
//        }
//        return CheckInMemory(OnlineChecker(InMemorySessionManager(choreographies)), failFast)
//    }

    fun createStoreInMemory(): InterceptStrategy {
        return StoreInMemory()
    }
}