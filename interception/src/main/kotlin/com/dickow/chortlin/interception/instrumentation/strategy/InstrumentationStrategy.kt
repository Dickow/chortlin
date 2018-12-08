package com.dickow.chortlin.inteception.instrumentation.strategy

import com.dickow.chortlin.inteception.instrumentation.strategy.factory.StrategyFactory

object InstrumentationStrategy {
    @JvmStatic
    var strategy: InterceptStrategy = StrategyFactory.createStoreInMemory()
}