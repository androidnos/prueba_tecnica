package com.example.prueba_tecnica.retrofit

import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class APIClientTest {

    private lateinit var apiClientTest: APIClient

    @Before
    fun setUp() {
        apiClientTest = APIClient()
    }

    @Test
    fun getClient() {
        /* When */
        val itemReturn = APIClient.getClient()

        /* Then */
        assertNotNull(itemReturn)
    }
}