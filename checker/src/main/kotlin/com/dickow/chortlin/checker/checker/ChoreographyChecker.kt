package com.dickow.chortlin.checker.checker

import com.dickow.chortlin.checker.checker.result.ChoreographyStatus
import com.dickow.chortlin.checker.trace.TraceElement
import com.dickow.chortlin.shared.trace.protobuf.DtoDefinitions

interface ChoreographyChecker {
    fun check(trace: TraceElement): ChoreographyStatus
    fun check(traceDTO: DtoDefinitions.InvocationDTO): ChoreographyStatus
    fun check(traceDTO: DtoDefinitions.ReturnDTO): ChoreographyStatus
}