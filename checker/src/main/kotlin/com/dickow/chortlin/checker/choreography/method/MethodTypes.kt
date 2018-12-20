package com.dickow.chortlin.checker.choreography.method

import com.dickow.chortlin.checker.choreography.participant.InternalParticipant
import java.lang.reflect.Method

interface ObservableMethod<C> {
    val jvmMethod: Method
    val participant: InternalParticipant<*>
}

class TypelessMethod<C>(override val jvmMethod: Method, override val participant: InternalParticipant<C>) : ObservableMethod<C>
class Method0<C, R>(override val jvmMethod: Method, signature: (C) -> R, override val participant: InternalParticipant<C>) : ObservableMethod<C>
class Method1<C, T1, R>(override val jvmMethod: Method, signature: (C, T1) -> R, override val participant: InternalParticipant<C>) : ObservableMethod<C>
class Method2<C, T1, T2, R>(override val jvmMethod: Method, signature: (C, T1, T2) -> R, override val participant: InternalParticipant<C>) : ObservableMethod<C>
class Method3<C, T1, T2, T3, R>(override val jvmMethod: Method, signature: (C, T1, T2, T3) -> R, override val participant: InternalParticipant<C>) : ObservableMethod<C>
class Method4<C, T1, T2, T3, T4, R>(override val jvmMethod: Method, signature: (C, T1, T2, T3, T4) -> R, override val participant: InternalParticipant<C>) : ObservableMethod<C>
