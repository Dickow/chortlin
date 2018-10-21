package com.dickow.chortlin.core.checker.pattern

import com.dickow.chortlin.core.trace.Trace

abstract class Pattern(protected val childNodes: MutableList<Pattern>) {
    abstract fun match(trace: Trace) : Boolean

    fun addChild(child: Pattern){
        childNodes.add(child)
    }
}