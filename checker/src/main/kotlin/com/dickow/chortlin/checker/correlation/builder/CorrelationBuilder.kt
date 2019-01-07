package com.dickow.chortlin.checker.correlation.builder

import com.dickow.chortlin.checker.choreography.participant.ObservableParticipant
import com.dickow.chortlin.checker.correlation.Correlation
import com.dickow.chortlin.checker.correlation.CorrelationFunction
import com.dickow.chortlin.checker.correlation.InputTypesFunction
import com.dickow.chortlin.checker.correlation.ReturnTypesFunction
import com.dickow.chortlin.checker.correlation.path.Path
import java.util.*

class CorrelationBuilder(private val observable: ObservableParticipant,
                         private val correlationFunction: CorrelationFunction)
{
    private val inputFunctions: MutableList<InputTypesFunction> = LinkedList()
    private val returnFunctions: MutableList<ReturnTypesFunction> = LinkedList()

    fun done(): Correlation {
        return Correlation(observable, correlationFunction, inputFunctions, returnFunctions)
    }

    fun noExtensions(): Correlation {
        return Correlation(observable, correlationFunction, emptyList(), emptyList())
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