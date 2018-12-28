package com.dickow.chortlin.interception.test

import com.dickow.chortlin.interception.annotations.Named
import com.dickow.chortlin.interception.dto.TraceDTOFactory
import com.dickow.chortlin.interception.observation.Observation
import com.google.protobuf.Value
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class TraceDTOFactoryTests {

    private val stringObservable = createObservation(PlaceholderTestClass::class.java, "stringMethod")
    private val intObservable = createObservation(PlaceholderTestClass::class.java, "intMethod")
    private val doubleObservable = createObservation(PlaceholderTestClass::class.java, "doubleMethod")
    private val booleanObservable = createObservation(PlaceholderTestClass::class.java, "booleanMethod")
    private val listObservable = createObservation(PlaceholderTestClass::class.java, "listMethod")
    private val arrayObservable = createObservation(PlaceholderTestClass::class.java, "arrayMethod")
    private val objectObservable = createObservation(PlaceholderTestClass::class.java, "objectMethod")

    private val factory = TraceDTOFactory()

    @Test
    fun `construct value for simple string`(){
        val valueForTest = "Hello World"
        val trace = factory.buildInvocationDTO(stringObservable, arrayOf(valueForTest))
        assertEquals(valueForTest, trace.getArguments(0).value.stringValue)
    }

    @Test
    fun `construct value for integer`(){
        val valueForTest = 100
        val trace = factory.buildInvocationDTO(intObservable, arrayOf(valueForTest))
        assertEquals(valueForTest.toDouble(), trace.getArguments(0).value.numberValue)
    }

    @Test
    fun `construct value for double`(){
        val valueForTest = 10.99
        val trace = factory.buildInvocationDTO(doubleObservable, arrayOf(valueForTest))
        assertEquals(valueForTest, trace.getArguments(0).value.numberValue)
    }

    @Test
    fun `construct value for boolean`(){
        val valueForTest = true
        val trace = factory.buildInvocationDTO(booleanObservable, arrayOf(valueForTest))
        assertEquals(valueForTest, trace.getArguments(0).value.boolValue)
        assertEquals("boolean", trace.getArguments(0).identifier)
    }

    @Test
    fun `construct value for list of strings`(){
        val valueForTest = listOf("Hello", "World", "!")
        val trace = factory.buildInvocationDTO(listObservable, arrayOf(valueForTest))
        assertEquals(valueForTest.size, trace.getArguments(0).value.listValue.valuesCount)
        assertEquals(valueForTest, trace.getArguments(0).value.listValue.valuesList.map { v -> v.stringValue })
    }

    @Test
    fun `construct value for array of strings`(){
        val valueForTest = arrayOf("Hello", "World", "!")
        val trace = factory.buildInvocationDTO(arrayObservable, arrayOf(valueForTest))
        assertEquals(valueForTest.size, trace.getArguments(0).value.listValue.valuesCount)
        assertTrue {
            Arrays.equals(valueForTest,
            trace.getArguments(0).value.listValue.valuesList.map { v -> v.stringValue }.toTypedArray())
        }
    }

    @Test
    fun `construct value for null input`(){
        val trace = factory.buildInvocationDTO(stringObservable, arrayOfNulls(1))
        assertEquals(trace.getArguments(0).value.kindCase, Value.KindCase.NULL_VALUE)
    }

    @Test
    fun `construct value for complex object`(){
        val valueForTest = ValueObject()
        val expectedFields = listOf("name", "id", "address", "otherNames", "nested")
        val trace = factory.buildInvocationDTO(objectObservable, arrayOf(valueForTest))
        assertTrue { trace.getArguments(0).value.hasStructValue() }
        val hasAllField = trace.getArguments(0).value.structValue.fieldsMap.keys.containsAll(expectedFields)
        assertTrue { hasAllField }
        assertEquals("object", trace.getArguments(0).identifier)
    }

    private fun createObservation(clazz:Class<*>, method:String) : Observation {
        return Observation(clazz, clazz.methods.single { m -> m.name == method })
    }
}

class PlaceholderTestClass {
    fun booleanMethod(@Named("boolean") boolean:Boolean){}
    fun stringMethod(string:String){}
    fun intMethod(integer:Int){}
    fun doubleMethod(double: Double){}
    fun objectMethod(@Named("object") obj: ValueObject){}
    fun listMethod(list:List<String>){}
    fun arrayMethod(array:Array<String>){}
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