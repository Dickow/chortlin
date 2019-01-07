package com.dickow.chortlin.shared.observation

abstract class Observable(val canonicalClassName: String, val method: String) {

    override fun equals(other: Any?): Boolean {
        return if (other is Observable) {
            this.canonicalClassName == other.canonicalClassName && this.method == other.method
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = method.hashCode()
        result = 31 * result + method.hashCode()
        return result
    }

    override fun toString(): String {
        return "$canonicalClassName::$method"
    }
}