package com.example.prueba_tecnica.base.presenter

import com.example.prueba_tecnica.base.BaseTestPresenterTest
import io.mockk.MockK
import io.mockk.spyk
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class BasePresenterTest : BaseTestPresenterTest() {

    private lateinit var testerBaseTest: BasePresenter<MockK>

    @Before
    fun init() {
        testerBaseTest = spyk(recordPrivateCalls = true)
    }

    @Test
    fun `Test attactView`() {
        // When
        testerBaseTest.attactView(mockkRelaxed(), mockkRelaxed())

        // Then
        Assert.assertNotNull(testerBaseTest.view)
        Assert.assertNotNull(testerBaseTest.apiInterface)
    }
}