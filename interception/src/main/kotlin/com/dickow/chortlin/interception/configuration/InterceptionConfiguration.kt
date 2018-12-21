package com.dickow.chortlin.interception.configuration

import com.dickow.chortlin.interception.defaults.DefaultIntercept
import com.dickow.chortlin.interception.instrumentation.ByteBuddyInstrumentation
import com.dickow.chortlin.interception.sending.TraceSender

object InterceptionConfiguration {
    @JvmStatic
    fun setupInterception(sender : TraceSender){
        ByteBuddyInstrumentation.instrumentAnnotatedMethods()
        InterceptionStrategy.strategy = DefaultIntercept(sender)
    }
}