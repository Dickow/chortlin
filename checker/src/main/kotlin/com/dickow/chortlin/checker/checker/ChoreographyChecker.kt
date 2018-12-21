package com.dickow.chortlin.checker.checker

import com.dickow.chortlin.checker.checker.result.CheckResult
import com.dickow.chortlin.shared.trace.TraceElement
import com.dickow.chortlin.shared.trace.dto.InvocationDTO
import com.dickow.chortlin.shared.trace.dto.ReturnDTO

interface ChoreographyChecker {
    fun check(trace: TraceElement): CheckResult
    fun check(traceDTO: InvocationDTO): CheckResult
    fun check(traceDTO: ReturnDTO): CheckResult
}