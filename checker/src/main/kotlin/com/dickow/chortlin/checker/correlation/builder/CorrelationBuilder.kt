package com.dickow.chortlin.checker.correlation.builder

import com.dickow.chortlin.checker.choreography.method.ObservableMethod
import com.dickow.chortlin.checker.correlation.Correlation
import com.dickow.chortlin.checker.correlation.CorrelationFunction
import com.dickow.chortlin.checker.correlation.InputTypesFunction
import com.dickow.chortlin.checker.correlation.ReturnTypesFunction
import com.dickow.chortlin.checker.correlation.path.Path
import com.dickow.chortlin.shared.observation.ObservableParticipant
import java.util.*

class CorrelationBuilder(private val observableMethod: ObservableMethod,
                         private val correlationFunction: CorrelationFunction) {
    private val inputFunctions: MutableList<InputTypesFunction> = LinkedList()
    private val returnFunctions: MutableList<ReturnTypesFunction> = LinkedList()

    fun done(): Correlation {
        val observableParticipant = ObservableParticipant(observableMethod.participant.clazz, observableMethod.jvmMethod)
        return Correlation(observableParticipant, correlationFunction, inputFunctions, returnFunctions)
    }

    fun noExtensions(): Correlation {
        val observableParticipant = ObservableParticipant(observableMethod.participant.clazz, observableMethod.jvmMethod)
        return Correlation(observableParticipant, correlationFunction, emptyList(), emptyList())
    }

    fun extendFromInput(key: String, path: Path): CorrelationBuilder {
        inputFunctions.add(InputTypesFunction(key, path))
        return this
    }

    fun extendFromReturn(key: String, path: Path): CorrelationBuilder {
        returnFunctions.add(ReturnTypesFunction(key, path))
        return this
    }
}