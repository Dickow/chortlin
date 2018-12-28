package com.dickow.chortlin.checker.checker

import com.dickow.chortlin.checker.checker.result.CheckResult
import com.dickow.chortlin.checker.trace.Trace

interface SatisfactionRelationship {
    fun satisfy(trace: Trace) : CheckResult
}