package com.dickow.chortlin.checker.choreography.method

import com.dickow.chortlin.checker.choreography.participant.InternalParticipant
import java.lang.reflect.Method

interface ObservableMethod {
    val jvmMethod: Method
    val participant: InternalParticipant<*>
}

class TypelessMethod(override val jvmMethod: Method, override val participant: InternalParticipant<*>) : ObservableMethod
