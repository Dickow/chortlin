package com.dickow.chortlin.checker.test

import com.dickow.chortlin.checker.correlation.builder.PathBuilder.Builder.root
import com.dickow.chortlin.shared.exceptions.ChoreographyRuntimeException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


class PathTests {

    private val input = mapOf(Pair("root", mapOf(Pair("fullName", mapOf(Pair("firstName", "Jeppe"))))))

    @Test
    fun `construct a simple path and check structure is as expected`() {
        val path = root().node("fullName").node("firstName").build()
        val retrievedFirstName = path.apply(input)
        assertEquals("Jeppe", retrievedFirstName)
    }

    @Test
    fun `expect error when retrieving property that does not exist`() {
        val path = root().node("email").build()
        assertFailsWith(ChoreographyRuntimeException::class) { path.apply(input) }
    }

    @Test
    fun `assert root only path returns entire object`() {
        val path = root().build()
        val expected = input["root"] as Any?
        assertEquals(expected, path.apply(input))
    }
}