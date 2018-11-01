package com.dickow.chortlin.core.checker.pattern

import com.dickow.chortlin.core.checker.match.Matcher
import com.dickow.chortlin.core.trace.Trace
import com.dickow.chortlin.core.trace.TraceElement

class SinglePattern(
        private val element: TraceElement,
        previous: Pattern?,
        child: Pattern?) :
        Pattern(previous, child) {

    private val matcher = Matcher()

    override fun getExpectedTraces(): List<TraceElement> {
        return listOf(element)
    }

    override fun match(trace: Trace): Boolean {
        val matchResult =
                matcher.matchOne(trace.getNotConsumed(), element) { t -> previous?.causalityRespected(t) ?: true }
        return when (matchResult.matched) {
            true -> {
                trace.consume(matchResult.matchedElements[0])
                super.setMatched(matchResult.matchedElements[0])
                return child?.match(trace) ?: true
            }
            false -> false
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