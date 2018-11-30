package com.dickow.chortlin.core.choreography.participant.observation

import com.dickow.chortlin.core.choreography.participant.Participant
import java.lang.reflect.Method

data class ObservedParticipant(override val clazz: Class<*>, override val method: Method) : Participant {
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