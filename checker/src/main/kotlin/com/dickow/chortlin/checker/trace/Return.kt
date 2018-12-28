package com.dickow.chortlin.checker.trace

import com.dickow.chortlin.shared.observation.Observable

data class Return(private val observation: Observable,
                  private val argumentTree: Map<*, *>,
                  val returnValue: Map<*, *>) : TraceElement() {
    override fun getArgumentTree(): Map<*, *> {
        return argumentTree
    }

    override fun getObservation(): Observable {
        return observation
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Return) return false

        if (observation != other.observation) return false

        return true
    }

    override fun hashCode(): Int {
        return observation.hashCode()
    }
}