package com.dickow.chortlin.core.api.endpoint

import java.util.*

class Endpoint(private val clazz: Class<*>, private val name: String) {

    override fun equals(other: Any?): Boolean {
        if (other is Endpoint) {
            return other.clazz.canonicalName == clazz.canonicalName && other.name == name
        }
        return false
    }

    override fun hashCode(): Int {
        return Objects.hash(clazz.canonicalName, name)
    }

    override fun toString(): String {
        return "${clazz.canonicalName}#$name"
    }
}