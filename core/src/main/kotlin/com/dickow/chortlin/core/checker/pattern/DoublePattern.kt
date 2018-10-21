package com.dickow.chortlin.core.checker.pattern

import com.dickow.chortlin.core.trace.Trace
import com.dickow.chortlin.core.trace.TraceElement

class DoublePattern<C1, C2>(
        private val element1: TraceElement<C1>,
        private val element2: TraceElement<C2>,
        childNodes: MutableList<Pattern>) : Pattern(childNodes)
{
    override fun match(trace: Trace): Boolean {
        return false
    }
}