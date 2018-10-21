package com.dickow.chortlin.core.checker.pattern

import com.dickow.chortlin.core.trace.Trace

class EmptyPattern(childNodes: MutableList<Pattern>) : Pattern(childNodes) {
    override fun match(trace: Trace): Boolean {
        return childNodes.all { child -> child.match(trace) }
    }
}