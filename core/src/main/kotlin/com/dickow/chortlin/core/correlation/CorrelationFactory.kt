@file:Suppress("UNCHECKED_CAST")

package com.dickow.chortlin.core.correlation

import com.dickow.chortlin.core.choreography.participant.ObservableParticipant
import com.dickow.chortlin.core.correlation.functiondefinitions.CFunc1
import com.dickow.chortlin.core.correlation.functiondefinitions.CFunc2
import com.dickow.chortlin.core.correlation.functiondefinitions.CFunc3
import com.dickow.chortlin.core.exceptions.InvalidChoreographyException
import com.dickow.chortlin.core.util.TypeUtil

object CorrelationFactory {
    fun <T> correlation(participant: ObservableParticipant<*>, correlationFunction: CFunc1<T>, vararg addFunctions: CorrelationFunction): Correlation {
        checkCorrelationFunctionMatch(participant, correlationFunction)
        val func = { args: Array<Any> ->
            correlationFunction(args[0] as T)
        }
        return Correlation(participant, InputTypesFunction(func))
    }

    fun <T1, T2> correlation(participant: ObservableParticipant<*>, correlationFunction: CFunc2<T1, T2>, vararg addFunctions: CorrelationFunction): Correlation {
        checkCorrelationFunctionMatch(participant, correlationFunction)
        val func = { args: Array<Any> ->
            correlationFunction(args[0] as T1, args[1] as T2)
        }
        return Correlation(participant, InputTypesFunction(func))
    }

    fun <T1, T2, T3> correlation(participant: ObservableParticipant<*>, correlationFunction: CFunc3<T1, T2, T3>, vararg addFunctions: CorrelationFunction): Correlation {
        checkCorrelationFunctionMatch(participant, correlationFunction)
        val func = { args: Array<Any> ->
            correlationFunction(args[0] as T1, args[1] as T2, args[2] as T3)
        }
        return Correlation(participant, InputTypesFunction(func))
    }

    fun <T> fromInput(addFunction: CFunc1<T>): CorrelationFunction {
        val func = { args: Array<Any> ->
            addFunction(args[0] as T)
        }
        return InputTypesFunction(func)
    }

    fun <T1, T2> fromInput(addFunction: CFunc2<T1, T2>): CorrelationFunction {
        val func = { args: Array<Any> ->
            addFunction(args[0] as T1, args[1] as T2)
        }
        return InputTypesFunction(func)
    }

    fun <T1, T2, T3> fromInput(addFunction: CFunc3<T1, T2, T3>): CorrelationFunction {
        val func = { args: Array<Any> ->
            addFunction(args[0] as T1, args[1] as T2, args[2] as T3)
        }
        return InputTypesFunction(func)
    }

    fun <T> fromReturn(addFunction: CFunc1<T>): CorrelationFunction {
        val func = { returnArg: Any -> addFunction(returnArg as T) }
        return ReturnTypesFunction(func)
    }

    private fun checkCorrelationFunctionMatch(participant: ObservableParticipant<*>, correlationFunction: Any) {
        (TypeUtil.getMethodMatch(participant.method.parameterTypes, correlationFunction::class.java.declaredMethods)
                ?: throw InvalidChoreographyException("Incorrect correlation function encountered for $participant"))
    }
}