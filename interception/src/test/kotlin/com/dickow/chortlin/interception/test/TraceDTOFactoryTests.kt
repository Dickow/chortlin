package com.dickow.chortlin.interception.test

import com.dickow.chortlin.interception.dto.TraceDTOFactory
import com.dickow.chortlin.interception.observation.Observation
import com.google.protobuf.Value
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class TraceDTOFactoryTests {

    private val testObservation = Observation(PlaceholderTestClass::class.java,
            PlaceholderTestClass::class.java.methods.single { m -> m.name == "method" })

    private val factory = TraceDTOFactory()

    @Test
    fun `construct value for simple string`(){
        val valueForTest = "Hello World"
        val trace = factory.buildInvocationDTO(testObservation, arrayOf(valueForTest))
        assertEquals(valueForTest, trace.getArguments(0).value.stringValue)
    }

    @Test
    fun `construct value for integer`(){
        val valueForTest = 100
        val trace = factory.buildInvocationDTO(testObservation, arrayOf(valueForTest))
        assertEquals(valueForTest.toDouble(), trace.getArguments(0).value.numberValue)
    }

    @Test
    fun `construct value for double`(){
        val valueForTest = 10.99
        val trace = factory.buildInvocationDTO(testObservation, arrayOf(valueForTest))
        assertEquals(valueForTest, trace.getArguments(0).value.numberValue)
    }

    @Test
    fun `construct value for boolean`(){
        val valueForTest = true
        val trace = factory.buildInvocationDTO(testObservation, arrayOf(valueForTest))
        assertEquals(valueForTest, trace.getArguments(0).value.boolValue)
    }

    @Test
    fun `construct value for list of strings`(){
        val valueForTest = listOf("Hello", "World", "!")
        val trace = factory.buildInvocationDTO(testObservation, arrayOf(valueForTest))
        assertEquals(valueForTest.size, trace.getArguments(0).value.listValue.valuesCount)
        assertEquals(valueForTest, trace.getArguments(0).value.listValue.valuesList.map { v -> v.stringValue })
    }

    @Test
    fun `construct value for array of strings`(){
        val valueForTest = arrayOf("Hello", "World", "!")
        val trace = factory.buildInvocationDTO(testObservation, arrayOf(valueForTest))
        assertEquals(valueForTest.size, trace.getArguments(0).value.listValue.valuesCount)
        assertTrue {
            Arrays.equals(valueForTest,
            trace.getArguments(0).value.listValue.valuesList.map { v -> v.stringValue }.toTypedArray())
        }
    }

    @Test
    fun `construct value for null input`(){
        val trace = factory.buildInvocationDTO(testObservation, arrayOfNulls(1))
        assertEquals(trace.getArguments(0).value.kindCase, Value.KindCase.NULL_VALUE)
    }

    @Test
    fun `construct value for complex object`(){
        val valueForTest = ValueObject()
        val expectedFields = listOf("name", "id", "address", "otherNames", "nested")
        val trace = factory.buildInvocationDTO(testObservation, arrayOf(valueForTest))
        assertTrue { trace.getArguments(0).value.hasStructValue() }
        val hasAllField = trace.getArguments(0).value.structValue.fieldsMap.keys.containsAll(expectedFields)
        assertTrue { hasAllField }
    }
}

class PlaceholderTestClass {
    fun method(){}
}

class ValueObject {
    var name = "Hello"
    var id = 999
    var address = "World 101"
    var otherNames = listOf("World", "Hello")
    val nested = NestedObject()
}

class NestedObject {
    val field1 = "FIELD 1"
    val field2 = true
}