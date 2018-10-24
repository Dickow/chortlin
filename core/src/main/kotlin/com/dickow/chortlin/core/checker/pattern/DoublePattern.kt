package com.dickow.chortlin.core.checker.pattern

import com.dickow.chortlin.core.trace.Trace
import com.dickow.chortlin.core.trace.TraceElement

class DoublePattern<C1, C2>(
        private val element1: TraceElement<C1>,
        private val element2: TraceElement<C2>,
        childNodes: MutableList<Pattern>) : Pattern(childNodes)
{
    override fun match(trace: Trace): Boolean {
        val traceList = trace.getNotConsumed()
        return if (traceList.isEmpty() || traceList.size < 2) {
            false
        } else {
            val element1 = traceList[0]
            val element2 = traceList[1]
            if (this.element1 == element1 && this.element2 == element2) {
                trace.consume(2)
                return childNodes.all { child -> child.match(trace) }
            } else {
                false
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        return if (other is DoublePattern<*, *>) {
            this.element1 == other.element1
                    && this.element2 == other.element2
                    && this.childNodes.size == other.childNodes.size
                    && this.childNodes == other.childNodes
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = element1.hashCode()
        result = 31 * result + element2.hashCode()
        return result
    }
}