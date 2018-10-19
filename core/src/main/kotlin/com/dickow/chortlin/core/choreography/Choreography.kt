package com.dickow.chortlin.core.choreography

import com.dickow.chortlin.core.ast.types.ASTNode
import com.dickow.chortlin.core.ast.types.Marker

data class Choreography(val start: ASTNode) {
    companion object {
        fun builder(): ChoreographyBuilder {
            return Marker()
        }
    }
}