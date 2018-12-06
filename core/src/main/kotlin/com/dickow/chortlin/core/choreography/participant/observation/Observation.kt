package com.dickow.chortlin.core.choreography.participant.observation

import java.lang.reflect.Method

data class Observation(override val clazz: Class<*>, override val method: Method) : Observable