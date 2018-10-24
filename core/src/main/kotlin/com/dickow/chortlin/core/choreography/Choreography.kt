package com.dickow.chortlin.core.choreography

import com.dickow.chortlin.core.ast.types.ASTNode
import com.dickow.chortlin.core.ast.types.Marker
import com.dickow.chortlin.core.checker.ChoreographyChecker

data class Choreography(val start: ASTNode) {
    companion object {
        fun builder(): ChoreographyBuilder {
            return Marker()
        }
    }

    fun createChecker(): ChoreographyChecker {
        return ChoreographyChecker(this)
    }
}