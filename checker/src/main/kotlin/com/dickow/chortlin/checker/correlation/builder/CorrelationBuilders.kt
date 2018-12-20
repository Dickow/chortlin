package com.dickow.chortlin.checker.correlation.builder

import com.dickow.chortlin.checker.choreography.method.ObservableMethod
import com.dickow.chortlin.checker.correlation.Correlation
import com.dickow.chortlin.checker.correlation.CorrelationFunction
import com.dickow.chortlin.checker.correlation.InputTypesFunction
import com.dickow.chortlin.checker.correlation.ReturnTypesFunction
import com.dickow.chortlin.checker.correlation.functiondefinitions.*
import com.dickow.chortlin.shared.observation.ObservableParticipant
import java.util.*

abstract class CorrelationBuilder(private val observableMethod: ObservableMethod<*>, private val correlationFunction: InputTypesFunction) {
    protected val additionFunctions: MutableList<CorrelationFunction> = LinkedList()
    fun done(): Correlation {
        val observableParticipant = ObservableParticipant(observableMethod.participant.clazz, observableMethod.jvmMethod)
        return Correlation(observableParticipant, correlationFunction, additionFunctions)
    }

    fun noExtensions(): Correlation {
        val observableParticipant = ObservableParticipant(observableMethod.participant.clazz, observableMethod.jvmMethod)
        return Correlation(observableParticipant, correlationFunction, emptyList())
    }

    protected fun <T> internalExtendFromReturn(key: String, addFunction: CFunc1<T>) {
        val func = { returnArg: Any? -> addFunction(returnArg as T) }
        additionFunctions.add(ReturnTypesFunction(key, func))
    }
}

class CorrelationBuilder0<R>(observableMethod: ObservableMethod<*>, correlationFunction: InputTypesFunction) : CorrelationBuilder(observableMethod, correlationFunction) {
    fun extendFromInput(key: String, keyFunction: CFunc0): CorrelationBuilder0<R> {
        val func = { _: Array<Any?> -> keyFunction() }
        additionFunctions.add(InputTypesFunction(key, func))
        return this
    }

    fun extendFromReturn(key: String, keyFunction: CFunc1<R>): CorrelationBuilder0<R> {
        super.internalExtendFromReturn(key, keyFunction)
        return this
    }
}

class CorrelationBuilder1<T1, R>(observableMethod: ObservableMethod<*>, correlationFunction: InputTypesFunction) : CorrelationBuilder(observableMethod, correlationFunction) {
    fun extendFromInput(key: String, keyFunction: CFunc1<T1>): CorrelationBuilder1<T1, R> {
        val func = { args: Array<Any?> ->
            keyFunction(args[0] as T1)
        }
        additionFunctions.add(InputTypesFunction(key, func))
        return this
    }

    fun extendFromReturn(key: String, keyFunction: CFunc1<R>): CorrelationBuilder1<T1, R> {
        super.internalExtendFromReturn(key, keyFunction)
        return this
    }
}

class CorrelationBuilder2<T1, T2, R>(observableMethod: ObservableMethod<*>, correlationFunction: InputTypesFunction) : CorrelationBuilder(observableMethod, correlationFunction) {
    fun extendFromInput(key: String, keyFunction: CFunc2<T1, T2>): CorrelationBuilder2<T1, T2, R> {
        val func = { args: Array<Any?> ->
            keyFunction(args[0] as T1, args[1] as T2)
        }
        additionFunctions.add(InputTypesFunction(key, func))
        return this
    }

    fun extendFromReturn(key: String, keyFunction: CFunc1<R>): CorrelationBuilder2<T1, T2, R> {
        super.internalExtendFromReturn(key, keyFunction)
        return this
    }
}

class CorrelationBuilder3<T1, T2, T3, R>(observableMethod: ObservableMethod<*>, correlationFunction: InputTypesFunction) : CorrelationBuilder(observableMethod, correlationFunction) {
    fun extendFromInput(key: String, keyFunction: CFunc3<T1, T2, T3>): CorrelationBuilder3<T1, T2, T3, R> {
        val func = { args: Array<Any?> ->
            keyFunction(args[0] as T1, args[1] as T2, args[2] as T3)
        }
        additionFunctions.add(InputTypesFunction(key, func))
        return this
    }

    fun extendFromReturn(key: String, keyFunction: CFunc1<R>): CorrelationBuilder3<T1, T2, T3, R> {
        super.internalExtendFromReturn(key, keyFunction)
        return this
    }
}

class CorrelationBuilder4<T1, T2, T3, T4, R>(observableMethod: ObservableMethod<*>, correlationFunction: InputTypesFunction) : CorrelationBuilder(observableMethod, correlationFunction) {
    fun extendFromInput(key: String, keyFunction: CFunc4<T1, T2, T3, T4>): CorrelationBuilder4<T1, T2, T3, T4, R> {
        val func = { args: Array<Any?> ->
            keyFunction(args[0] as T1, args[1] as T2, args[2] as T3, args[3] as T4)
        }
        additionFunctions.add(InputTypesFunction(key, func))
        return this
    }

    fun extendFromReturn(key: String, keyFunction: CFunc1<R>): CorrelationBuilder4<T1, T2, T3, T4, R> {
        super.internalExtendFromReturn(key, keyFunction)
        return this
    }
}