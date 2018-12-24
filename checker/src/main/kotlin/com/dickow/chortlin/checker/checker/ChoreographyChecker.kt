package com.dickow.chortlin.checker.checker

import com.dickow.chortlin.checker.checker.result.CheckResult
import com.dickow.chortlin.shared.trace.TraceElement
import com.dickow.chortlin.shared.trace.protobuf.DtoDefinitions

interface ChoreographyChecker {
    fun check(trace: TraceElement): CheckResult
    fun check(traceDTO: DtoDefinitions.InvocationDTO): CheckResult
    fun check(traceDTO: DtoDefinitions.ReturnDTO): CheckResult
}