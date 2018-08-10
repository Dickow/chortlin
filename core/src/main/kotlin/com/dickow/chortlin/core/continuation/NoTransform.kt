package com.dickow.chortlin.core.continuation

class NoTransform<T> : Transform<T, T> {
    override fun transform(value: T): T {
        return value
    }
}