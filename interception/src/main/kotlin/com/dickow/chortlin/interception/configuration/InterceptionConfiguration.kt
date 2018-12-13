package com.dickow.chortlin.interception.configuration

import com.dickow.chortlin.interception.defaults.ChortlinIntercept
import com.dickow.chortlin.interception.instrumentation.ByteBuddyInstrumentation
import com.dickow.chortlin.interception.sending.ChortlinSender

object InterceptionConfiguration {
    @JvmStatic
    fun setupInterception(sender : ChortlinSender){
        ByteBuddyInstrumentation.instrumentAnnotatedMethods()
        InterceptionStrategy.strategy = ChortlinIntercept(sender)
    }
}