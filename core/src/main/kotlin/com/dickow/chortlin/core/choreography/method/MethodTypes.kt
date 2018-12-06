package com.dickow.chortlin.core.choreography.method

import com.dickow.chortlin.core.choreography.participant.InternalParticipant
import java.lang.reflect.Method

interface ChortlinMethod<C> {
    val jvmMethod: Method
    val participant: InternalParticipant<*>
}

class ChortlinMethod0<C, R>(override val jvmMethod: Method, signature: (C) -> R, override val participant: InternalParticipant<*>) : ChortlinMethod<C>
class ChortlinMethod1<C, T1, R>(override val jvmMethod: Method, signature: (C, T1) -> R, override val participant: InternalParticipant<*>) : ChortlinMethod<C>
class ChortlinMethod2<C, T1, T2, R>(override val jvmMethod: Method, signature: (C, T1, T2) -> R, override val participant: InternalParticipant<*>) : ChortlinMethod<C>
class ChortlinMethod3<C, T1, T2, T3, R>(override val jvmMethod: Method, signature: (C, T1, T2, T3) -> R, override val participant: InternalParticipant<*>) : ChortlinMethod<C>
class ChortlinMethod4<C, T1, T2, T3, T4, R>(override val jvmMethod: Method, signature: (C, T1, T2, T3, T4) -> R, override val participant: InternalParticipant<*>) : ChortlinMethod<C>
