package com.dickow.chortlin.core.choreography.participant.observation

import java.lang.reflect.Method

data class ObservableParticipant(override val clazz: Class<*>, override val method: Method) : Observable