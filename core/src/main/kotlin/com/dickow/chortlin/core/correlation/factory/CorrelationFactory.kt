@file:Suppress("UNCHECKED_CAST")

package com.dickow.chortlin.core.correlation.factory

import com.dickow.chortlin.core.choreography.method.*
import com.dickow.chortlin.core.correlation.CorrelationFunction
import com.dickow.chortlin.core.correlation.InputTypesFunction
import com.dickow.chortlin.core.correlation.ReturnTypesFunction
import com.dickow.chortlin.core.correlation.builder.*
import com.dickow.chortlin.core.correlation.functiondefinitions.*

object CorrelationFactory {

    @JvmStatic
    fun defineCorrelation(): CorrelationParticipantMappingBuilder {
        return CorrelationParticipantMappingBuilder()
    }

    @JvmStatic
    fun <R> correlation(method: ChortlinMethod0<*, R>, key: String, correlationFunction: CFunc0): CorrelationBuilder0<R> {
        val func = { _: Array<Any> -> correlationFunction() }
        return CorrelationBuilder0(method, InputTypesFunction(key, func))
    }

    @JvmStatic
    fun <T1, R> correlation(method: ChortlinMethod1<*, T1, R>, key: String, correlationFunction: CFunc1<T1>): CorrelationBuilder1<T1, R> {
        val func = { args: Array<Any> ->
            correlationFunction(args[0] as T1)
        }
        return CorrelationBuilder1(method, InputTypesFunction(key, func))
    }

    @JvmStatic
    fun <T1, T2, R> correlation(method: ChortlinMethod2<*, T1, T2, R>, key: String, correlationFunction: CFunc2<T1, T2>): CorrelationBuilder2<T1, T2, R> {
        val func = { args: Array<Any> ->
            correlationFunction(args[0] as T1, args[1] as T2)
        }
        return CorrelationBuilder2(method, InputTypesFunction(key, func))
    }

    @JvmStatic
    fun <T1, T2, T3, R> correlation(method: ChortlinMethod3<*, T1, T2, T3, R>, key: String, correlationFunction: CFunc3<T1, T2, T3>): CorrelationBuilder3<T1, T2, T3, R> {
        val func = { args: Array<Any> ->
            correlationFunction(args[0] as T1, args[1] as T2, args[2] as T3)
        }
        return CorrelationBuilder3(method, InputTypesFunction(key, func))
    }

    @JvmStatic
    fun <T1, T2, T3, T4, R> correlation(method: ChortlinMethod4<*, T1, T2, T3, T4, R>, key: String, correlationFunction: CFunc4<T1, T2, T3, T4>): CorrelationBuilder4<T1, T2, T3, T4, R> {
        val func = { args: Array<Any> ->
            correlationFunction(args[0] as T1, args[1] as T2, args[2] as T3, args[3] as T4)
        }
        return CorrelationBuilder4(method, InputTypesFunction(key, func))
    }

    @JvmStatic
    fun fromInput(key: String, addFunction: CFunc0): CorrelationFunction {
        val func = { _: Array<Any> -> addFunction() }
        return InputTypesFunction(key, func)
    }

    @JvmStatic
    fun <T> fromInput(key: String, addFunction: CFunc1<T>): CorrelationFunction {
        val func = { args: Array<Any> ->
            addFunction(args[0] as T)
        }
        return InputTypesFunction(key, func)
    }

    @JvmStatic
    fun <T1, T2> fromInput(key: String, addFunction: CFunc2<T1, T2>): CorrelationFunction {
        val func = { args: Array<Any> ->
            addFunction(args[0] as T1, args[1] as T2)
        }
        return InputTypesFunction(key, func)
    }

    @JvmStatic
    fun <T1, T2, T3> fromInput(key: String, addFunction: CFunc3<T1, T2, T3>): CorrelationFunction {
        val func = { args: Array<Any> ->
            addFunction(args[0] as T1, args[1] as T2, args[2] as T3)
        }
        return InputTypesFunction(key, func)
    }

    @JvmStatic
    fun <T1, T2, T3, T4> fromInput(key: String, addFunction: CFunc4<T1, T2, T3, T4>): CorrelationFunction {
        val func = { args: Array<Any> ->
            addFunction(args[0] as T1, args[1] as T2, args[2] as T3, args[3] as T4)
        }
        return InputTypesFunction(key, func)
    }

    @JvmStatic
    fun <T> fromReturn(key: String, addFunction: CFunc1<T>): CorrelationFunction {
        val func = { returnArg: Any -> addFunction(returnArg as T) }
        return ReturnTypesFunction(key, func)
    }
}