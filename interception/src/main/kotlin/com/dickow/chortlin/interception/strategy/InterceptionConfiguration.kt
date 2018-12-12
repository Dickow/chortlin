package com.dickow.chortlin.interception.strategy

import com.dickow.chortlin.interception.instrumentation.ByteBuddyInstrumentation

object InterceptionConfiguration {
    @JvmStatic
    fun setupInterception(sender : ChortlinSender){
        ByteBuddyInstrumentation.instrumentAnnotatedMethods()
        InstrumentationStrategy.strategy = ChortlinIntercept(sender)
    }
}