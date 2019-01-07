package com.dickow.chortlin.checker.trace.value

class NullValue : Value {
    override fun equals(other: Any?): Boolean {
        return other is NullValue
    }

    override fun hashCode(): Int {
        return this.javaClass.canonicalName.hashCode()
    }
}