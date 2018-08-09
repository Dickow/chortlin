package com.dickow.chortlin.testmodule.kotlin

import com.dickow.chortlin.core.continuation.Transform

class KotlinNoTransform<TIn> : Transform<TIn, TIn> {
    override fun transform(value: TIn): TIn {
        return value
    }
}