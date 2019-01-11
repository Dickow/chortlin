package com.dickow.chortlin.checker.checker

import com.dickow.chortlin.checker.checker.result.ChoreographyStatus
import com.dickow.chortlin.checker.trace.TraceEvent
import com.dickow.chortlin.shared.trace.protobuf.DtoDefinitions

interface ChoreographyChecker {
    fun check(trace: TraceEvent): ChoreographyStatus
    fun check(traceDTO: DtoDefinitions.InvocationDTO): ChoreographyStatus
    fun check(traceDTO: DtoDefinitions.ReturnDTO): ChoreographyStatus
}