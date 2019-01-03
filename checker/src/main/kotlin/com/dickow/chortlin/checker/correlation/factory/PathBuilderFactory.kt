package com.dickow.chortlin.checker.correlation.factory

import com.dickow.chortlin.checker.correlation.builder.PathBuilder

object PathBuilderFactory {
    @JvmStatic
    fun root(): PathBuilder {
        return PathBuilder()
    }
}