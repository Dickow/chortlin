package com.dickow.chortlin.interception.instrumentation.strategy

import com.dickow.chortlin.interception.instrumentation.strategy.factory.StrategyFactory

object InstrumentationStrategy {
    @JvmStatic
    var strategy: InterceptStrategy = StrategyFactory.createStoreInMemory()
}