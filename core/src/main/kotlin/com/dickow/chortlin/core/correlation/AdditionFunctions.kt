package com.dickow.chortlin.core.correlation

open class AdditionFunctions(val additionFunctions: List<CorrelationFunction>)

class AdditionFunctions0<R>(additionFunctions: List<CorrelationFunction>) : AdditionFunctions(additionFunctions)
class AdditionFunctions1<T1, R>(additionFunctions: List<CorrelationFunction>) : AdditionFunctions(additionFunctions)
class AdditionFunctions2<T1, T2, R>(additionFunctions: List<CorrelationFunction>) : AdditionFunctions(additionFunctions)
class AdditionFunctions3<T1, T2, T3, R>(additionFunctions: List<CorrelationFunction>) : AdditionFunctions(additionFunctions)
class AdditionFunctions4<T1, T2, T3, T4, R>(additionFunctions: List<CorrelationFunction>) : AdditionFunctions(additionFunctions)