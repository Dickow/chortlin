package com.dickow.chortlin.interception.strategy


object InstrumentationStrategy {
    @JvmStatic
    var strategy: InterceptStrategy = DoNothingStrategy()
}