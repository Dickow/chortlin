package com.dickow.chortlin.core.test.networkinterception

import com.dickow.chortlin.core.JsonInterceptor
import com.dickow.chortlin.core.test.shared.Authentication
import com.dickow.chortlin.shared.observation.ObservableFactory
import com.dickow.chortlin.shared.trace.Invocation
import kotlin.test.Test

class SerializedInterceptionValuesTests {
    private val interceptor = JsonInterceptor()

    @Test
    fun `observe simple trace and send it across the serialisation`(){
        val observed = ObservableFactory.observed(Authentication::class.java, "authenticate")
        val trace = Invocation(observed, arrayOf("jeppeDickow", "password"))
        interceptor.intercept(trace)
    }
}