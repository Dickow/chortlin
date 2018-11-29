@file:Suppress("UNCHECKED_CAST")

package com.dickow.chortlin.core.correlation.factory

import com.dickow.chortlin.core.choreography.participant.ObservableParticipant
import com.dickow.chortlin.core.correlation.Correlation
import com.dickow.chortlin.core.correlation.CorrelationFunction
import com.dickow.chortlin.core.correlation.InputTypesFunction
import com.dickow.chortlin.core.correlation.ReturnTypesFunction
import com.dickow.chortlin.core.correlation.functiondefinitions.CFunc0
import com.dickow.chortlin.core.correlation.functiondefinitions.CFunc1
import com.dickow.chortlin.core.correlation.functiondefinitions.CFunc2
import com.dickow.chortlin.core.correlation.functiondefinitions.CFunc3
import com.dickow.chortlin.core.exceptions.InvalidChoreographyException

object CorrelationFactory {

    fun correlation(participant: ObservableParticipant<*>, correlationFunction: CFunc0, vararg addFunctions: CorrelationFunction): Correlation {
        val func = { _: Array<Any> -> correlationFunction() }
        return create(participant, InputTypesFunction(func, correlationFunction.javaClass.declaredMethods), addFunctions)
    }

    fun <T> correlation(participant: ObservableParticipant<*>, correlationFunction: CFunc1<T>, vararg addFunctions: CorrelationFunction): Correlation {
        val func = { args: Array<Any> ->
            correlationFunction(args[0] as T)
        }
        return create(participant, InputTypesFunction(func, correlationFunction.javaClass.declaredMethods), addFunctions)
    }

    fun <T1, T2> correlation(participant: ObservableParticipant<*>, correlationFunction: CFunc2<T1, T2>, vararg addFunctions: CorrelationFunction): Correlation {
        val func = { args: Array<Any> ->
            correlationFunction(args[0] as T1, args[1] as T2)
        }
        return create(participant, InputTypesFunction(func, correlationFunction.javaClass.declaredMethods), addFunctions)
    }

    fun <T1, T2, T3> correlation(participant: ObservableParticipant<*>, correlationFunction: CFunc3<T1, T2, T3>, vararg addFunctions: CorrelationFunction): Correlation {
        val func = { args: Array<Any> ->
            correlationFunction(args[0] as T1, args[1] as T2, args[2] as T3)
        }
        return create(participant, InputTypesFunction(func, correlationFunction.javaClass.declaredMethods), addFunctions)
    }

    fun fromInput(addFunction: CFunc0): CorrelationFunction {
        val func = { _: Array<Any> -> addFunction() }
        return InputTypesFunction(func, addFunction.javaClass.declaredMethods)
    }

    fun <T> fromInput(addFunction: CFunc1<T>): CorrelationFunction {
        val func = { args: Array<Any> ->
            addFunction(args[0] as T)
        }
        return InputTypesFunction(func, addFunction.javaClass.declaredMethods)
    }

    fun <T1, T2> fromInput(addFunction: CFunc2<T1, T2>): CorrelationFunction {
        val func = { args: Array<Any> ->
            addFunction(args[0] as T1, args[1] as T2)
        }
        return InputTypesFunction(func, addFunction.javaClass.declaredMethods)
    }

    fun <T1, T2, T3> fromInput(addFunction: CFunc3<T1, T2, T3>): CorrelationFunction {
        val func = { args: Array<Any> ->
            addFunction(args[0] as T1, args[1] as T2, args[2] as T3)
        }
        return InputTypesFunction(func, addFunction.javaClass.declaredMethods)
    }

    fun fromReturn(addFunction: CFunc0): CorrelationFunction {
        val func = { _: Any -> addFunction() }
        return ReturnTypesFunction(func, addFunction.javaClass.declaredMethods)
    }

    fun <T> fromReturn(addFunction: CFunc1<T>): CorrelationFunction {
        val func = { returnArg: Any -> addFunction(returnArg as T) }
        return ReturnTypesFunction(func, addFunction.javaClass.declaredMethods)
    }

    private fun create(
            participant: ObservableParticipant<*>,
            correlationFunction: InputTypesFunction,
            addFunctions: Array<out CorrelationFunction>): Correlation {

        for (func in arrayOf<CorrelationFunction>(correlationFunction).plus(addFunctions)) {
            if (!func.applicableTo(participant)) {
                throw InvalidChoreographyException("Encountered a correlation function with invalid type definitions." + System.lineSeparator() +
                        "The error was found for the participant $participant" + System.lineSeparator() +
                        "The wrong function was $func")
            }
        }

        return Correlation(participant, correlationFunction, addFunctions.toList())
    }
}