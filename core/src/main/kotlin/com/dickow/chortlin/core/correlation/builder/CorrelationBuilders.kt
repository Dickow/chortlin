package com.dickow.chortlin.core.correlation.builder

import com.dickow.chortlin.core.choreography.participant.Participant
import com.dickow.chortlin.core.correlation.Correlation
import com.dickow.chortlin.core.correlation.CorrelationFunction
import com.dickow.chortlin.core.correlation.InputTypesFunction
import com.dickow.chortlin.core.correlation.factory.CorrelationFactory
import com.dickow.chortlin.core.correlation.functiondefinitions.*
import java.util.*

abstract class CorrelationBuilder(private val participant: Participant, private val correlationFunction: InputTypesFunction) {
    protected val additionFunctions: MutableList<CorrelationFunction> = LinkedList()
    fun done(): Correlation {
        return Correlation(participant, correlationFunction, additionFunctions)
    }

    fun noExtensions(): Correlation {
        return Correlation(participant, correlationFunction, emptyList())
    }
}

class CorrelationBuilder0<R>(participant: Participant, correlationFunction: InputTypesFunction) : CorrelationBuilder(participant, correlationFunction) {
    fun extendFromInput(keyFunction: CFunc0): CorrelationBuilder0<R> {
        additionFunctions.add(CorrelationFactory.fromInput(keyFunction))
        return this
    }

    fun extendFromReturn(keyFunction: CFunc1<R>): CorrelationBuilder0<R> {
        additionFunctions.add(CorrelationFactory.fromReturn(keyFunction))
        return this
    }
}

class CorrelationBuilder1<T1, R>(participant: Participant, correlationFunction: InputTypesFunction) : CorrelationBuilder(participant, correlationFunction) {
    fun extendFromInput(keyFunction: CFunc1<T1>): CorrelationBuilder1<T1, R> {
        additionFunctions.add(CorrelationFactory.fromInput(keyFunction))
        return this
    }

    fun extendFromReturn(keyFunction: CFunc1<R>): CorrelationBuilder1<T1, R> {
        additionFunctions.add(CorrelationFactory.fromReturn(keyFunction))
        return this
    }
}

class CorrelationBuilder2<T1, T2, R>(participant: Participant, correlationFunction: InputTypesFunction) : CorrelationBuilder(participant, correlationFunction) {
    fun extendFromInput(keyFunction: CFunc2<T1, T2>): CorrelationBuilder2<T1, T2, R> {
        additionFunctions.add(CorrelationFactory.fromInput(keyFunction))
        return this
    }

    fun extendFromReturn(keyFunction: CFunc1<R>): CorrelationBuilder2<T1, T2, R> {
        additionFunctions.add(CorrelationFactory.fromReturn(keyFunction))
        return this
    }
}

class CorrelationBuilder3<T1, T2, T3, R>(participant: Participant, correlationFunction: InputTypesFunction) : CorrelationBuilder(participant, correlationFunction) {
    fun extendFromInput(keyFunction: CFunc3<T1, T2, T3>): CorrelationBuilder3<T1, T2, T3, R> {
        additionFunctions.add(CorrelationFactory.fromInput(keyFunction))
        return this
    }

    fun extendFromReturn(keyFunction: CFunc1<R>): CorrelationBuilder3<T1, T2, T3, R> {
        additionFunctions.add(CorrelationFactory.fromReturn(keyFunction))
        return this
    }
}

class CorrelationBuilder4<T1, T2, T3, T4, R>(participant: Participant, correlationFunction: InputTypesFunction) : CorrelationBuilder(participant, correlationFunction) {
    fun extendFromInput(keyFunction: CFunc4<T1, T2, T3, T4>): CorrelationBuilder4<T1, T2, T3, T4, R> {
        additionFunctions.add(CorrelationFactory.fromInput(keyFunction))
        return this
    }

    fun extendFromReturn(keyFunction: CFunc1<R>): CorrelationBuilder4<T1, T2, T3, T4, R> {
        additionFunctions.add(CorrelationFactory.fromReturn(keyFunction))
        return this
    }
}