package com.dickow.chortlin.core.correlation.builder

import com.dickow.chortlin.core.correlation.Correlation
import com.dickow.chortlin.core.correlation.CorrelationSet
import java.util.*

class CorrelationSetBuilder {
    private val correlationSet: MutableList<Correlation> = LinkedList()

    fun add(correlation: Correlation): CorrelationSetBuilder {
        this.correlationSet.add(correlation)
        return this
    }

    fun finish(): CorrelationSet {
        return CorrelationSet(correlationSet)
    }
}