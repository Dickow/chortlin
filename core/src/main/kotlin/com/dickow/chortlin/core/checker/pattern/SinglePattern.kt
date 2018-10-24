package com.dickow.chortlin.core.checker.pattern

import com.dickow.chortlin.core.trace.Trace
import com.dickow.chortlin.core.trace.TraceElement

class SinglePattern<C>(private val element : TraceElement<C>, childNodes: MutableList<Pattern>) : Pattern(childNodes) {
    override fun match(trace: Trace): Boolean {
        val traceList = trace.getNotConsumed()
        return if (traceList.isEmpty()) {
            false
        } else {
            val element = traceList.first()
            if (this.element == element) {
                trace.consume(1)
                return childNodes.all { child -> child.match(trace) }
            } else {
                false
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        return if (other is SinglePattern<*>) {
            this.element == other.element
                    && this.childNodes.size == other.childNodes.size
                    && this.childNodes == other.childNodes
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return element.hashCode()
    }
}