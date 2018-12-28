package com.dickow.chortlin.interception.configuration

import com.dickow.chortlin.interception.defaults.DefaultInterceptor
import com.dickow.chortlin.interception.defaults.HttpSender
import com.dickow.chortlin.interception.instrumentation.ByteBuddyInstrumentation
import com.dickow.chortlin.interception.sending.TraceSender

object InterceptionConfiguration {
    @JvmStatic
    fun setupCustomInterception(sender: TraceSender) {
        ByteBuddyInstrumentation.instrumentAnnotatedMethods()
        InterceptionStrategy.strategy = DefaultInterceptor(sender)
    }

    @JvmStatic
    fun setupForHttpInterception(configuration: HttpConfiguration) {
        ByteBuddyInstrumentation.instrumentAnnotatedMethods()
        InterceptionStrategy.strategy = DefaultInterceptor(HttpSender(configuration))
    }
}