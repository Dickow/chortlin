package com.dickow.chortlin.checker.test

import com.dickow.chortlin.checker.trace.value.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ValueTests {

    @Test
    fun `test that equality of boolean values`() {
        assertEquals(BooleanValue(true), BooleanValue(true))
        assertNotEquals(BooleanValue(true), BooleanValue(false))
        assertNotEquals<Value>(BooleanValue(true), NumberValue(10.1))
    }

    @Test
    fun `test equality of list value`() {
        val list1 = ListValue(listOf(BooleanValue(true), NumberValue(20), StringValue("Hello")))
        val list2 = ListValue(listOf(BooleanValue(true), NumberValue(20), StringValue("Hello")))
        val list3 = ListValue(listOf(NumberValue(20), StringValue("Hello"), BooleanValue(true)))
        assertEquals(list1, list2)
        assertNotEquals(list1, list3)
    }

    @Test
    fun `test equality for null value`() {
        assertEquals(NullValue(), NullValue())
        assertNotEquals<Value>(NullValue(), BooleanValue(true))
        assertEquals(NullValue().hashCode(), NullValue().hashCode())
    }

    @Test
    fun `test equality of number values`() {
        assertEquals(NumberValue(10), NumberValue(10))
        assertNotEquals(NumberValue(11), NumberValue(10))
        assertEquals(NumberValue(10.1), NumberValue(10.1))
        assertNotEquals(NumberValue(10.0), NumberValue(10)) // maybe
    }

    @Test
    fun `test equality of object value`() {
        val obj1 = ObjectValue(mapOf(Pair("key1", NumberValue(10)), Pair("key2", StringValue("Hello"))))
        val obj2 = ObjectValue(mapOf(Pair("key1", NumberValue(10)), Pair("key2", StringValue("Hello"))))
        val obj3 = ObjectValue(mapOf(Pair("key2", StringValue("Hello")), Pair("key1", NumberValue(10))))
        val obj4 = ObjectValue(mapOf(Pair("key2", StringValue("Hello"))))

        assertEquals(ObjectValue(mapOf()), ObjectValue(mapOf()))
        assertEquals(obj1, obj2)
        assertEquals(obj1, obj3)
        assertNotEquals(obj1, obj4)
    }

    @Test
    fun `test equality of root value`() {
        val root1 = RootValue(ListValue(listOf(NumberValue(10), BooleanValue(true))))
        val root2 = RootValue(ListValue(listOf(NumberValue(10), BooleanValue(true))))
        val root3 = RootValue(ObjectValue(mapOf(Pair("key", NumberValue(20)))))
        assertEquals(RootValue(StringValue("Hello")), RootValue(StringValue("Hello")))
        assertEquals(root1, root2)
        assertNotEquals(root1, root3)
    }

    @Test
    fun `test equality for string value`() {
        assertEquals(StringValue("Hello"), StringValue(("Hello")))
        assertNotEquals(StringValue("hello"), StringValue("Hello"))
    }
}