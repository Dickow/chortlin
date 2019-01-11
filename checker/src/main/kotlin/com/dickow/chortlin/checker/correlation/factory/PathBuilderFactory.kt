package com.dickow.chortlin.checker.correlation.factory

import com.dickow.chortlin.checker.correlation.builder.PathBuilder
import com.dickow.chortlin.checker.correlation.path.Path
import com.dickow.chortlin.checker.correlation.path.Root

object PathBuilderFactory {
    @JvmStatic
    fun root(): Path {
        return Root()
    }

    @JvmStatic
    fun node(key: String): PathBuilder {
        return PathBuilder().node(key)
    }
}