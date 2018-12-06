package com.dickow.chortlin.core.choreography.participant.observation

import java.lang.reflect.Method

interface Observable {
    val clazz: Class<*>
    val method: Method
}