package com.example.prueba_tecnica.retrofit

import com.example.prueba_tecnica.base.BaseTest
import org.junit.Assert.assertNotNull
import org.junit.Test

class APIClientTest : BaseTest() {

    @Test
    fun getClient() {
        /* When */
        val itemReturn = APIClient.getClient(mockkRelaxed())

        /* Then */
        assertNotNull(itemReturn)
    }
}