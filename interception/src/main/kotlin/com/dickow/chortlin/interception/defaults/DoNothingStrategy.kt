package com.dickow.chortlin.interception.defaults

import com.dickow.chortlin.interception.InterceptStrategy
import com.dickow.chortlin.shared.observation.Observable

class DoNothingStrategy : InterceptStrategy {
    override fun interceptInvocation(observable: Observable, arguments: Array<out Any?>) {
    }

    override fun interceptReturn(observable: Observable, arguments: Array<out Any?>, returnValue: Any?) {
    }
}