package com.dickow.chortlin.core.test.interaction

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.test.Test

class DynamicProxyingTest {
    @Test
    fun `override method call with dynamic proxy`() {
        val obj = ClassToBeProxied()
        val proxy = Proxy.newProxyInstance(this.javaClass.classLoader, ClassToBeProxied().javaClass.interfaces, ProxyClass(obj)) as ChortlinInterface
        proxy.method2("lols")
    }

    private interface ChortlinInterface {
        fun method(a: Int, b: String, c: Int): String
        fun method2(a: String)
        fun method3(a: String): String
    }

    private class ClassToBeProxied : ChortlinInterface {
        override fun method(a: Int, b: String, c: Int): String {
            return "I was invoked"
        }

        override fun method2(a: String) {

        }

        override fun method3(a: String): String {
            return a
        }

        fun hiddenMethod(): Int {
            return 42
        }
    }

    private class ProxyClass constructor(private val obj: Any) : InvocationHandler {
        override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
            println("Proxy invoked")
            return method!!.invoke(obj, args)
        }
    }
}