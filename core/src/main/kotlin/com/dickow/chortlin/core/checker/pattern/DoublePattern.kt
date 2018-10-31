package com.dickow.chortlin.core.checker.pattern

import com.dickow.chortlin.core.trace.Trace
import com.dickow.chortlin.core.trace.TraceElement
import com.dickow.chortlin.core.trace.TraceElementIndexed

class DoublePattern(
        private val element1: TraceElement,
        private val element2: TraceElement,
        previous: Pattern?,
        child: Pattern?) : Pattern(previous, child)
{
    override fun match(trace: Trace): Boolean {
        val traceList = trace.getNotConsumed()
        return if (traceList.isEmpty() || traceList.size < 2) {
            false
        } else {
            val firstElement = getFirstMatchingElement(traceList)
            if (firstElement != null) {
                val secondElement = getSecondMatchingElement(traceList, firstElement)
                if (secondElement != null) {
                    trace.consume(firstElement, secondElement)
                    super.setMatched(secondElement)
                    return child?.match(trace) ?: true
                } else {
                    false
                }
            } else {
                false
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        return if (other is DoublePattern) {
            this.element1 == other.element1
                    && this.element2 == other.element2
                    && this.child == other.child
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = element1.hashCode()
        result = 31 * result + element2.hashCode()
        return result
    }

    private fun getSecondMatchingElement(
            traceList: MutableList<TraceElementIndexed>, firstElement: TraceElementIndexed): TraceElementIndexed? {
        return traceList.firstOrNull { t ->
            t.traceElement == element2 && t.index > firstElement.index
        }
    }

    private fun getFirstMatchingElement(traceList: MutableList<TraceElementIndexed>): TraceElementIndexed? {
        return traceList.firstOrNull { t ->
            t.traceElement == element1 && (if (previous == null) true else previous?.causalityRespected(t) ?: false)
        }
    }
}