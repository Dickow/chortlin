package com.dickow.chortlin.interception.instrumentation.strategy.factory

import com.dickow.chortlin.interception.instrumentation.strategy.InterceptStrategy
import com.dickow.chortlin.interception.instrumentation.strategy.StoreInMemory

object StrategyFactory {

    fun createStoreInMemory(): InterceptStrategy {
        return StoreInMemory()
    }
}