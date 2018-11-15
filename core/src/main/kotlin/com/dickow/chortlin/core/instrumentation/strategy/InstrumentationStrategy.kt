package com.dickow.chortlin.core.instrumentation.strategy

import com.dickow.chortlin.core.instrumentation.strategy.factory.StrategyFactory

object InstrumentationStrategy {
    @JvmStatic
    var strategy: InterceptStrategy = StrategyFactory.createStoreInMemory()
}