package com.dickow.chortlin.core.types.path

import com.dickow.chortlin.core.types.choreography.Choreography
import com.dickow.chortlin.core.types.choreography.PathChoreography

interface Path {
    fun sequence(nextPath: Path): Path {
        return Sequence(this, nextPath)
    }

    fun optional(nextPath: Path): Path {
        return Optional(this, nextPath)
    }

    fun finish(): Choreography {
        return PathChoreography(this)
    }
}