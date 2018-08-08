package com.dickow.chortlin.core.test.interaction.lookup

import com.dickow.chortlin.core.api.endpoint.Endpoint
import com.dickow.chortlin.core.configuration.lookup.ILookup
import com.dickow.chortlin.core.configuration.lookup.InMemoryLookup
import kotlin.test.*

class LookupTests {

    private var lookup: ILookup = InMemoryLookup()
    private val configurationFactory = TestConfigurationFactory()

    // Reset the stored configurations for every test.
    @BeforeTest
    fun setUp() {
        lookup = InMemoryLookup()
    }

    @Test
    fun `insert and retrieve trigger`() {
        val trigger1 = configurationFactory.createTrigger(LookupTests::class.java, "endpoint1")
        val trigger2 = configurationFactory.createTrigger(LookupTests::class.java, "endpoint2")

        lookup.add(trigger1)
        lookup.add(trigger2)

        val retrieved = lookup.lookup(Endpoint(LookupTests::class.java, "endpoint1"))
        assertEquals(trigger1, retrieved)
        assertNotEquals(trigger2, retrieved)
    }

    @Test
    fun `insert and retrieve nested interaction`() {
        val trigger = configurationFactory.createTrigger(
                LookupTests::class.java, "endpoint1",
                listOf(configurationFactory.createInteraction(LookupTests::class.java, "endpoint2")))
        lookup.add(trigger)
        val interaction = lookup.lookup(Endpoint(LookupTests::class.java, "endpoint2"))
        val expected = trigger.interactions.stream().findFirst().get()
        assertEquals(expected, interaction)
        assertNotEquals(trigger, interaction)
    }

    @Test
    fun `retrieve endpoint that does not exist`() {
        val retrieved = lookup.lookup(Endpoint(LookupTests::class.java, "endpoint2"))
        assertNull(retrieved)
    }

    fun endpoint1() {
    }

    fun endpoint2() {
    }
}