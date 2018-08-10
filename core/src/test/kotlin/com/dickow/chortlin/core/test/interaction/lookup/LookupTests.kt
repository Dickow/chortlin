package com.dickow.chortlin.core.test.interaction.lookup

import com.dickow.chortlin.core.api.endpoint.Endpoint
import com.dickow.chortlin.core.configuration.lookup.InMemoryLookup
import com.dickow.chortlin.core.configuration.lookup.Lookup
import kotlin.test.*

class LookupTests {

    private var lookup: Lookup = InMemoryLookup()
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

        val endpoint = Endpoint(LookupTests::class.java, "endpoint1")
        val retrieved = lookup.lookup(endpoint, endpoint)
        assertEquals(trigger1, retrieved)
        assertNotEquals(trigger2, retrieved)
    }

    @Test
    fun `insert and retrieve nested interaction`() {
        val trigger = configurationFactory.createTrigger(
                LookupTests::class.java, "endpoint1",
                listOf(configurationFactory.createInteraction(LookupTests::class.java, "endpoint2")))
        lookup.add(trigger)
        val endpoint = Endpoint(LookupTests::class.java, "endpoint2")
        val interaction = lookup.lookup(endpoint, endpoint)
        val expected = trigger.continuations.stream().findFirst().get().getConfiguration()
        assertEquals(expected, interaction)
        assertNotEquals(trigger, interaction)
    }

    @Test
    fun `retrieve endpoint that does not exist`() {
        val endpoint = Endpoint(LookupTests::class.java, "endpoint2")
        val retrieved = lookup.lookup(endpoint, endpoint)
        assertNull(retrieved)
    }
}