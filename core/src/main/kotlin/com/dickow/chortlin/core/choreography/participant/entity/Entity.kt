package com.dickow.chortlin.core.choreography.participant.entity

abstract class Entity(val identifier: String) {
    override fun equals(other: Any?): Boolean {
        return if (other is Entity) {
            this.identifier == other.identifier
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return identifier.hashCode()
    }
}