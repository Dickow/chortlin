package com.dickow.chortlin.core.checker

import com.dickow.chortlin.core.checker.result.CheckResult
import com.dickow.chortlin.core.trace.Trace

interface SatisfactionRelationship {
    fun satisfy(trace: Trace) : CheckResult
}