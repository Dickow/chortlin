package com.dickow.chortlin.interception.test

import com.dickow.chortlin.interception.configuration.HttpConfiguration
import com.dickow.chortlin.interception.configuration.InterceptionConfiguration
import com.dickow.chortlin.interception.configuration.InterceptionStrategy
import com.dickow.chortlin.interception.configuration.defaults.DefaultInterceptor
import kotlin.test.Test
import kotlin.test.assertTrue

class ConfigurationTests {

    @Test
    fun `test that http configuration can be built`() {
        val config = HttpConfiguration(
                "api/invocation",
                "api/return")
        InterceptionConfiguration.setupForHttpInterception(config)

        val strategy = InterceptionStrategy.strategy
        assertTrue { strategy is DefaultInterceptor }
    }
}