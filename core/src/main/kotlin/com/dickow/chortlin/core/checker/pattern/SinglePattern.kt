package com.dickow.chortlin.core.checker.pattern

import com.dickow.chortlin.core.trace.Trace
import com.dickow.chortlin.core.trace.TraceElement

class SinglePattern(
        private val element: TraceElement,
        previous: Pattern?,
        child: Pattern?) :
        Pattern(previous, child) {

    override fun match(trace: Trace): Boolean {
        val traceList = trace.getNotConsumed()
        return if (traceList.isEmpty()) {
            false
        } else {
            val element = traceList.firstOrNull { t -> t.traceElement == this.element && causalityRespected(t) }
            if (element != null) {
                trace.consume(element)
                super.setMatched(element)
                return child?.match(trace) ?: true
            } else {
                false
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        return if (other is SinglePattern) {
            this.element == other.element
                    && this.child == other.child
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return element.hashCode()
    }
}