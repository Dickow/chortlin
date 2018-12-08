package com.dickow.chortlin.checker.correlation.builder

import com.dickow.chortlin.checker.choreography.method.ChortlinMethod
import com.dickow.chortlin.checker.correlation.*
import com.dickow.chortlin.checker.correlation.factory.CorrelationFactory
import com.dickow.chortlin.checker.correlation.functiondefinitions.*
import com.dickow.chortlin.shared.observation.ObservableParticipant
import java.util.*

abstract class CorrelationBuilder(private val method: ChortlinMethod<*>, private val correlationFunction: InputTypesFunction) {
    protected val additionFunctions: MutableList<CorrelationFunction> = LinkedList()
    fun done(): Correlation {
        val observableParticipant = ObservableParticipant(method.participant.clazz, method.jvmMethod)
        return Correlation(observableParticipant, correlationFunction, additionFunctions)
    }

    fun noExtensions(): Correlation {
        val observableParticipant = ObservableParticipant(method.participant.clazz, method.jvmMethod)
        return Correlation(observableParticipant, correlationFunction, emptyList())
    }
}

class CorrelationBuilder0<R>(method: ChortlinMethod<*>, correlationFunction: InputTypesFunction) : CorrelationBuilder(method, correlationFunction) {
    fun extendFromInput(key: String, keyFunction: CFunc0): CorrelationBuilder0<R> {
        additionFunctions.add(CorrelationFactory.fromInput(key, keyFunction))
        return this
    }

    fun extendFromReturn(key: String, keyFunction: CFunc1<R>): CorrelationBuilder0<R> {
        additionFunctions.add(CorrelationFactory.fromReturn(key, keyFunction))
        return this
    }
}

class CorrelationBuilder1<T1, R>(method: ChortlinMethod<*>, correlationFunction: InputTypesFunction) : CorrelationBuilder(method, correlationFunction) {
    fun extendFromInput(key: String, keyFunction: CFunc1<T1>): CorrelationBuilder1<T1, R> {
        additionFunctions.add(CorrelationFactory.fromInput(key, keyFunction))
        return this
    }

    fun extendFromReturn(key: String, keyFunction: CFunc1<R>): CorrelationBuilder1<T1, R> {
        additionFunctions.add(CorrelationFactory.fromReturn(key, keyFunction))
        return this
    }
}

class CorrelationBuilder2<T1, T2, R>(method: ChortlinMethod<*>, correlationFunction: InputTypesFunction) : CorrelationBuilder(method, correlationFunction) {
    fun extendFromInput(key: String, keyFunction: CFunc2<T1, T2>): CorrelationBuilder2<T1, T2, R> {
        additionFunctions.add(CorrelationFactory.fromInput(key, keyFunction))
        return this
    }

    fun extendFromReturn(key: String, keyFunction: CFunc1<R>): CorrelationBuilder2<T1, T2, R> {
        additionFunctions.add(CorrelationFactory.fromReturn(key, keyFunction))
        return this
    }
}

class CorrelationBuilder3<T1, T2, T3, R>(method: ChortlinMethod<*>, correlationFunction: InputTypesFunction) : CorrelationBuilder(method, correlationFunction) {
    fun extendFromInput(key: String, keyFunction: CFunc3<T1, T2, T3>): CorrelationBuilder3<T1, T2, T3, R> {
        additionFunctions.add(CorrelationFactory.fromInput(key, keyFunction))
        return this
    }

    fun extendFromReturn(key: String, keyFunction: CFunc1<R>): CorrelationBuilder3<T1, T2, T3, R> {
        additionFunctions.add(CorrelationFactory.fromReturn(key, keyFunction))
        return this
    }
}

class CorrelationBuilder4<T1, T2, T3, T4, R>(method: ChortlinMethod<*>, correlationFunction: InputTypesFunction) : CorrelationBuilder(method, correlationFunction) {
    fun extendFromInput(key: String, keyFunction: CFunc4<T1, T2, T3, T4>): CorrelationBuilder4<T1, T2, T3, T4, R> {
        additionFunctions.add(CorrelationFactory.fromInput(key, keyFunction))
        return this
    }

    fun extendFromReturn(key: String, keyFunction: CFunc1<R>): CorrelationBuilder4<T1, T2, T3, T4, R> {
        additionFunctions.add(CorrelationFactory.fromReturn(key, keyFunction))
        return this
    }
}