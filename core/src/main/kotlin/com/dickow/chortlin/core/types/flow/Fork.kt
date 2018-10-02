package com.dickow.chortlin.core.types.flow

import com.dickow.chortlin.core.api.exceptions.TypeApiExceptionFactory
import com.dickow.chortlin.core.types.path.Path
import java.util.function.Function

class Fork(val leftPath: Path, vararg forkedPaths: Function<Path, Path>) : Flow {
    val rightPaths = forkedPaths.map { function -> function.apply(leftPath) }

    init {
        if (forkedPaths.size <= 1) {
            throw TypeApiExceptionFactory.invalidForkConfiguration(forkedPaths)
        }
    }
}