package com.dickow.chortlin.core.types.flow

import com.dickow.chortlin.core.api.exceptions.TypeApiExceptionFactory
import com.dickow.chortlin.core.types.path.Path
import java.util.function.Function

class Choice(val leftPath: Path, vararg choicedPaths: Function<Path, Path>) : Flow {
    val rightPaths = choicedPaths.map { function -> function.apply(leftPath) }

    init {
        if (choicedPaths.size <= 1) {
            throw TypeApiExceptionFactory.invalidChoiceConfiguration(choicedPaths)
        }
    }
}