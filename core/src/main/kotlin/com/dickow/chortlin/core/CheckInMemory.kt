package com.dickow.chortlin.core

import com.dickow.chortlin.checker.checker.OnlineChecker
import com.dickow.chortlin.checker.checker.result.CheckResult
import com.dickow.chortlin.interception.instrumentation.strategy.InterceptStrategy
import com.dickow.chortlin.shared.exceptions.ChortlinRuntimeException
import com.dickow.chortlin.shared.trace.TraceElement
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