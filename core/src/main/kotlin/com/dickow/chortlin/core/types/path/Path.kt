package com.dickow.chortlin.core.types.path

import com.dickow.chortlin.core.types.choreography.Choreography
import com.dickow.chortlin.core.types.choreography.PathChoreography
import com.dickow.chortlin.core.types.flow.Choice
import com.dickow.chortlin.core.types.flow.Flow
import com.dickow.chortlin.core.types.flow.Fork
import com.dickow.chortlin.core.types.flow.MultiInstance
import java.util.function.Function

interface Path {
    fun sequence(nextPath: Path): Path {
        return Sequence(this, nextPath)
    }

    fun optional(nextPath: Path): Path {
        return Optional(this, nextPath)
    }

    fun loop(loopedPath: Path): Path {
        return Loop(this, loopedPath)
    }

    @SafeVarargs
    fun fork(vararg paths: Function<Path, Path>): Flow {
        return Fork(this, *paths)
    }

    @SafeVarargs
    fun choice(vararg paths: Function<Path, Path>): Flow {
        return Choice(this, *paths)
    }

    fun multiInstance(path: Function<Path, Path>): Flow {
        return MultiInstance(this, path)
    }

    fun finish(): Choreography {
        return PathChoreography(this)
    }
}