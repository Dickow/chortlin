package com.dickow.chortlin.core.choreography.participant

import com.dickow.chortlin.core.choreography.participant.entity.InternalEntity
import java.lang.reflect.Method

interface Participant {
    val clazz: Class<*>
    val method: Method
    fun nonObservable() = NonObservableParticipant(InternalEntity(clazz))
}

class Participant0<R>(override val clazz: Class<*>, override val method: Method) : Participant {
    override fun equals(other: Any?): Boolean {
        return if (other is Participant) {
            this.clazz == other.clazz && this.method == other.method
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = clazz.hashCode()
        result = 31 * result + method.hashCode()
        return result
    }
}

class Participant1<T, R>(override val clazz: Class<*>, override val method: Method) : Participant {
    override fun equals(other: Any?): Boolean {
        return if (other is Participant) {
            this.clazz == other.clazz && this.method == other.method
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = clazz.hashCode()
        result = 31 * result + method.hashCode()
        return result
    }
}

class Participant2<T1, T2, R>(override val clazz: Class<*>, override val method: Method) : Participant {
    override fun equals(other: Any?): Boolean {
        return if (other is Participant) {
            this.clazz == other.clazz && this.method == other.method
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = clazz.hashCode()
        result = 31 * result + method.hashCode()
        return result
    }
}

class Participant3<T1, T2, T3, R>(override val clazz: Class<*>, override val method: Method) : Participant {
    override fun equals(other: Any?): Boolean {
        return if (other is Participant) {
            this.clazz == other.clazz && this.method == other.method
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = clazz.hashCode()
        result = 31 * result + method.hashCode()
        return result
    }
}

class Participant4<T1, T2, T3, T4, R>(override val clazz: Class<*>, override val method: Method) : Participant {
    override fun equals(other: Any?): Boolean {
        return if (other is Participant) {
            this.clazz == other.clazz && this.method == other.method
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = clazz.hashCode()
        result = 31 * result + method.hashCode()
        return result
    }
}