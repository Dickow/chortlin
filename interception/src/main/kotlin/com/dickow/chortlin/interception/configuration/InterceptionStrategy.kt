package com.dickow.chortlin.interception.configuration

import com.dickow.chortlin.interception.InterceptStrategy
import com.dickow.chortlin.interception.configuration.defaults.DoNothingStrategy


object InterceptionStrategy {
    @JvmStatic
    var strategy: InterceptStrategy = DoNothingStrategy()
}