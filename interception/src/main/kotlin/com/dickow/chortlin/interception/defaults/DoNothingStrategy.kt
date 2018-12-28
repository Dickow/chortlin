package com.dickow.chortlin.interception.defaults

import com.dickow.chortlin.interception.InterceptStrategy
import com.dickow.chortlin.interception.observation.Observation

class DoNothingStrategy : InterceptStrategy {

    override fun interceptInvocation(observation: Observation, arguments: Array<out Any?>) {
    }

    override fun interceptReturn(observation: Observation, arguments: Array<out Any?>, returnValue: Any?) {
    }
}