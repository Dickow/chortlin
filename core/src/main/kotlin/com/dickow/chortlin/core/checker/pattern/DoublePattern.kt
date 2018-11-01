package com.dickow.chortlin.core.checker.pattern

import com.dickow.chortlin.core.checker.match.Matcher
import com.dickow.chortlin.core.trace.Trace
import com.dickow.chortlin.core.trace.TraceElement

class DoublePattern(
        private val element1: TraceElement,
        private val element2: TraceElement,
        previous: Pattern?,
        child: Pattern?) : Pattern(previous, child)
{
    private val matcher = Matcher()

    override fun getExpectedTraces(): List<TraceElement> {
        return listOf(element1, element2)
    }

    override fun match(trace: Trace): Boolean {
        val matchResult = matcher.matchTwo(trace.getNotConsumed(), element1, element2)
        { t -> previous?.causalityRespected(t) ?: true }
        return when (matchResult.matched) {
            true -> {
                trace.consume(matchResult.matchedElements[0], matchResult.matchedElements[1])
                super.setMatched(matchResult.matchedElements[1])
                return child?.match(trace) ?: true
            }
            false -> false
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
}