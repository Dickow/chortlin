package com.dickow.chortlin.core.test.interaction.shared

import com.dickow.chortlin.core.continuation.Transform

class NoTransform<TIn> : Transform<TIn, TIn> {
    override fun transform(value: TIn): TIn {
        return value
    }
}