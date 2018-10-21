package com.dickow.chortlin.core.checker.pattern

import com.dickow.chortlin.core.trace.Trace
import com.dickow.chortlin.core.trace.TraceElement

class SinglePattern<C>(private val element : TraceElement<C>, childNodes: MutableList<Pattern>) : Pattern(childNodes) {
    override fun match(trace: Trace): Boolean {
        return false
    }
}