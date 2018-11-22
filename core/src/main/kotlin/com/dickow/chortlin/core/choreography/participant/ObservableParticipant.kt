package com.dickow.chortlin.core.choreography.participant

import com.dickow.chortlin.core.choreography.participant.entity.InternalEntity
import java.lang.reflect.Method

data class ObservableParticipant<T>(val clazz: Class<T>, val method: Method) {
    val nonObservable = NonObservableParticipant(InternalEntity(clazz))
}