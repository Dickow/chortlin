package com.dickow.chortlin.interception

import com.dickow.chortlin.interception.observation.Observation

interface InterceptStrategy {
    fun interceptInvocation(observation: Observation, arguments: Array<out Any?>)
    fun interceptReturn(observation: Observation, arguments: Array<out Any?>, returnValue: Any?)
}