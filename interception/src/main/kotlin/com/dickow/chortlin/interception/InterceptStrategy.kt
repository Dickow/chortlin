package com.dickow.chortlin.interception

import com.dickow.chortlin.shared.observation.Observable

interface InterceptStrategy {
    fun interceptInvocation(observable: Observable, arguments: Array<out Any?>)
    fun interceptReturn(observable: Observable, arguments: Array<out Any?>, returnValue: Any?)
}