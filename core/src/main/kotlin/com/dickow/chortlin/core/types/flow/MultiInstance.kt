package com.dickow.chortlin.core.types.flow

import com.dickow.chortlin.core.types.path.Path
import java.util.function.Function

class MultiInstance(val leftPath: Path, multiInstancedPath: Function<Path, Path>) : Flow {
    val rightPath: Path = multiInstancedPath.apply(leftPath)
}