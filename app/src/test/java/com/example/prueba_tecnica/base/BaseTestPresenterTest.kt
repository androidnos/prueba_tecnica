package com.example.prueba_tecnica.base

import com.example.prueba_tecnica.retrofit.APIInterface
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

open class BaseTestPresenterTest: BaseTest() {

    open val throwable: Throwable = mockkRelaxed()
    open val apiInterface: APIInterface = mockkRelaxed()

    @Before
    open fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    open fun tearDown() {
        clearAllMocks()
        unmockkAll()
    }
}