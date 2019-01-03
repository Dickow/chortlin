package com.dickow.chortlin.checker.correlation.builder

import com.dickow.chortlin.checker.correlation.path.Node
import com.dickow.chortlin.checker.correlation.path.Path
import java.util.*

class PathBuilder {
    private val path = LinkedList<Node>()

    init {
        path.add(Node("root"))
    }

    fun node(key: String): PathBuilder {
        path.add(Node(key))
        return this
    }

    fun build(): Path {
        path.forEachIndexed { index, node ->
            if (path.size > index + 1) {
                node.next = path[index + 1]
            }
        }
        return path[0]
    }
}