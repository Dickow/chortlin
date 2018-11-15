package com.dickow.chortlin.core.instrumentation.strategy

import com.dickow.chortlin.core.checker.OnlineChecker
import com.dickow.chortlin.core.checker.result.CheckResult
import com.dickow.chortlin.core.exceptions.ChortlinRuntimeException
import com.dickow.chortlin.core.trace.TraceElement
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CheckInMemory(private val checker: OnlineChecker, private val failFast: Boolean) : InterceptStrategy {

    override fun intercept(trace: TraceElement) {
        if (failFast) {
            val result = checker.check(trace)
            when (result) {
                CheckResult.None ->
                    throw ChortlinRuntimeException("Error occurred in running choreography for trace: $trace")
                else -> {
                }
            }
        } else {
            GlobalScope.launch {
                checker.check(trace)
            }
        }
    }
}