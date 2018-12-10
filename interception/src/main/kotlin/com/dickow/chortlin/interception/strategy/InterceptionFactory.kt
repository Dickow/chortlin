package com.dickow.chortlin.interception.strategy

import com.dickow.chortlin.interception.instrumentation.ByteBuddyInstrumentation

object InterceptionFactory {
    @JvmStatic
    fun setupInterception(sender : ChortlinSender){
        ByteBuddyInstrumentation.instrumentAnnotatedMethods()
        InstrumentationStrategy.strategy = ChortlinIntercept(sender)
    }
}