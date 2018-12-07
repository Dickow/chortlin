package com.dickow.chortlin.core.choreography.participant.observation

import java.lang.reflect.Method

class ObservableParticipant(clazz: Class<*>, method: Method) : Observable(clazz, method)